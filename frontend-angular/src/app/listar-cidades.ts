import { Component, OnInit } from '@angular/core';
import { ImportsModule } from './imports';
import { Cidade } from '@domain/cidade';
import { ProjetoService } from '@service/projeto-service';
import { CadastrarCidade } from './cadastrar-cidade';
import { ListarComercios } from './listar-comercios';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'listar-cidades',
  templateUrl: 'listar-cidades.html',
  standalone: true,
  imports: [
    ImportsModule,
    CadastrarCidade,
    ListarComercios
  ],
  providers: [
    ProjetoService,
    MessageService
  ]
})
export class ListarCidades implements OnInit {

  public listaCidades: Cidade[] = [];
  public cidadeSelecionada: Cidade = new Cidade();
  public mostraJanelaCadastro: boolean = false;

  constructor(
    private service: ProjetoService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.pesquisarCidades();
  }

  private pesquisarCidades(): void {
    this.service.pesquisarCidades().subscribe({
      next: (cidades: Cidade[]) => {
        this.listaCidades = cidades;
      },
      error: (erro) => {
        console.error('Erro ao carregar cidades:', erro);

        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível carregar as cidades.'
        });
      }
    });
  }

  public abreJanelaParaCadastrarNovaCidade(): void {
    this.cidadeSelecionada = new Cidade();
    this.mostraJanelaCadastro = true;
  }

  public abreJanelaParaAlterarCidade(cidade: Cidade): void {
    this.cidadeSelecionada = new Cidade();
    this.cidadeSelecionada.id = cidade.id;
    this.cidadeSelecionada.nome = cidade.nome;
    this.cidadeSelecionada.uf = cidade.uf;
    this.cidadeSelecionada.capital = cidade.capital;

    this.mostraJanelaCadastro = true;
  }

  public excluir(cidade: Cidade): void {
    const confirmou = window.confirm(
      `Deseja excluir a cidade "${cidade.nome}"?`
    );

    if (!confirmou) {
      return;
    }

    this.service.excluir(cidade).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: `Cidade "${cidade.nome}" excluída com sucesso!`
        });

        this.pesquisarCidades();
      },
      error: (erro) => {
        console.error('Erro ao excluir cidade:', erro);

        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível excluir a cidade.'
        });
      }
    });
  }

  public fechaJanelaCadastro(salvou: boolean): void {
    this.mostraJanelaCadastro = false;

    if (salvou) {
      this.pesquisarCidades();
    }
  }
}
