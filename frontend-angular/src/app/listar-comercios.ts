import { Component, OnInit } from '@angular/core';
import { ImportsModule } from './imports';
import { Comercio } from '@domain/comercio';
import { Cidade } from '@domain/cidade';
import { ComercioService } from '@service/comercio-service';
import { ProjetoService } from '@service/projeto-service';
import { CadastrarComercio } from './cadastrar-comercio';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'listar-comercios',
  templateUrl: 'listar-comercios.html',
  standalone: true,
  imports: [
    ImportsModule,
    CadastrarComercio
  ],
  providers: [
    ComercioService,
    ProjetoService
  ]
})
export class ListarComercios implements OnInit {

  public listaComercios: Comercio[] = [];
  public cidades: Cidade[] = [];

  public comercioSelecionado: Comercio =
    new Comercio();

  public mostraJanelaCadastro: boolean = false;
  public carregando: boolean = false;

  constructor(
    private comercioService: ComercioService,
    private projetoService: ProjetoService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.pesquisarComercios();
    this.pesquisarCidades();
  }

  private pesquisarCidades(): void {
    this.projetoService.pesquisarCidades().subscribe({
      next: (cidades: Cidade[]) => {
        this.cidades = cidades;
      },
      error: (erro) => {
        console.error('Erro ao carregar cidades:', erro);
      }
    });
  }

  public pesquisarComercios(): void {
    this.carregando = true;

    this.comercioService.pesquisarComercios().subscribe({
      next: (comercios: Comercio[]) => {
        this.listaComercios = comercios;
        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao carregar comércios:', erro);
        this.carregando = false;

        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível carregar os comércios.'
        });
      }
    });
  }

  public abrirCadastro(): void {
    this.comercioSelecionado = new Comercio();
    this.mostraJanelaCadastro = true;
  }

  public abrirAlteracao(comercio: Comercio): void {
    this.comercioSelecionado = new Comercio();

    this.comercioSelecionado.id = comercio.id;
    this.comercioSelecionado.nomeComercio =
      comercio.nomeComercio;

    this.comercioSelecionado.nomeResponsavel =
      comercio.nomeResponsavel;

    this.comercioSelecionado.tipoComercio =
      comercio.tipoComercio;

    this.comercioSelecionado.cidadeId =
      comercio.cidadeId;

    this.mostraJanelaCadastro = true;
  }

  public fecharCadastro(salvou: boolean): void {
    this.mostraJanelaCadastro = false;

    if (salvou) {
      this.pesquisarComercios();
    }
  }

  public excluir(comercio: Comercio): void {
    if (comercio.id === undefined) {
      return;
    }

    const confirmou = window.confirm(
      `Deseja excluir o comércio "${comercio.nomeComercio}"?`
    );

    if (!confirmou) {
      return;
    }

    this.comercioService.excluir(comercio).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Comércio excluído com sucesso!'
        });

        this.pesquisarComercios();
      },
      error: (erro) => {
        console.error('Erro ao excluir comércio:', erro);

        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível excluir o comércio.'
        });
      }
    });
  }

  public nomeCidade(cidadeId?: number): string {
    if (!cidadeId) {
      return '-';
    }

    const cidade = this.cidades.find(
      item => item.id === cidadeId
    );

    if (!cidade) {
      return `Cidade ${cidadeId}`;
    }

    return `${cidade.nome} - ${cidade.uf}`;
  }

  public formatarTipo(tipo: string): string {
    const tipos: Record<string, string> = {
      FARMACIA: 'Farmácia',
      PADARIA: 'Padaria',
      POSTO_GASOLINA: 'Posto de gasolina',
      LANCHONETE: 'Lanchonete'
    };

    return tipos[tipo] ?? tipo;
  }
}
