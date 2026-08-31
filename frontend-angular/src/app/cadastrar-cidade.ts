import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ImportsModule } from './imports';
import { Cidade } from '@domain/cidade';
import { ProjetoService } from '@service/projeto-service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'cadastrar-cidade',
  templateUrl: 'cadastrar-cidade.html',
  standalone: true,
  imports: [ImportsModule],
  providers: [ProjetoService]
})
export class CadastrarCidade {

  @Input()
  public cidade: Cidade = new Cidade();

  @Output('onClose')
  private eventoFechaJanela = new EventEmitter<boolean>();

  constructor(
    private service: ProjetoService,
    private messageService: MessageService
  ) {}

  public salvar(): void {
    if (!this.cidade.nome?.trim()) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atenção',
        detail: 'Informe o nome da cidade.'
      });
      return;
    }

    if (!this.cidade.uf?.trim() || this.cidade.uf.length !== 2) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atenção',
        detail: 'A UF deve possuir 2 caracteres.'
      });
      return;
    }

    this.cidade.nome = this.cidade.nome.trim();
    this.cidade.uf = this.cidade.uf.trim().toUpperCase();

    this.service.salvar(this.cidade).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: this.cidade.id
            ? 'Cidade alterada com sucesso!'
            : 'Cidade cadastrada com sucesso!'
        });

        this.eventoFechaJanela.emit(true);
      },
      error: (erro) => {
        console.error('Erro ao salvar cidade:', erro);

        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível salvar a cidade.'
        });
      }
    });
  }

  public cancelar(): void {
    this.eventoFechaJanela.emit(false);
  }
}
