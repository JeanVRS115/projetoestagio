import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';

import { ImportsModule } from './imports';
import { Comercio } from '@domain/comercio';
import { Cidade } from '@domain/cidade';
import { ComercioService } from '@service/comercio-service';
import { ProjetoService } from '@service/projeto-service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'cadastrar-comercio',
  templateUrl: 'cadastrar-comercio.html',
  standalone: true,
  imports: [ImportsModule],
  providers: [
    ComercioService,
    ProjetoService
  ]
})
export class CadastrarComercio implements OnInit {

  @Input()
  public comercio: Comercio = new Comercio();

  @Output('onClose')
  private eventoFechaJanela =
    new EventEmitter<boolean>();

  public cidades: Cidade[] = [];

  public tiposComercio = [
    {
      label: 'Farmácia',
      value: 'FARMACIA'
    },
    {
      label: 'Padaria',
      value: 'PADARIA'
    },
    {
      label: 'Posto de gasolina',
      value: 'POSTO_GASOLINA'
    },
    {
      label: 'Lanchonete',
      value: 'LANCHONETE'
    }
  ];

  constructor(
    private comercioService: ComercioService,
    private projetoService: ProjetoService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.carregarCidades();
  }

  private carregarCidades(): void {
    this.projetoService.pesquisarCidades().subscribe({
      next: (cidades: Cidade[]) => {
        this.cidades = cidades;
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

  public salvar(): void {
    if (!this.comercio.nomeComercio.trim()) {
      this.exibirAviso('Informe o nome do comércio.');
      return;
    }

    if (!this.comercio.nomeResponsavel.trim()) {
      this.exibirAviso('Informe o nome do responsável.');
      return;
    }

    if (!this.comercio.tipoComercio) {
      this.exibirAviso('Selecione o tipo de comércio.');
      return;
    }

    if (!this.comercio.cidadeId) {
      this.exibirAviso('Selecione a cidade.');
      return;
    }

    this.comercioService.salvar(this.comercio).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: this.comercio.id
            ? 'Comércio alterado com sucesso!'
            : 'Comércio cadastrado com sucesso!'
        });

        this.eventoFechaJanela.emit(true);
      },
      error: (erro) => {
        console.error('Erro ao salvar comércio:', erro);

        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível salvar o comércio.'
        });
      }
    });
  }

  public cancelar(): void {
    this.eventoFechaJanela.emit(false);
  }

  private exibirAviso(mensagem: string): void {
    this.messageService.add({
      severity: 'warn',
      summary: 'Atenção',
      detail: mensagem
    });
  }
}
