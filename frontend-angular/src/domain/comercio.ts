export type TipoComercio =
  | 'FARMACIA'
  | 'PADARIA'
  | 'POSTO_GASOLINA'
  | 'LANCHONETE';

export class Comercio {
  id?: number;
  nomeComercio: string = '';
  nomeResponsavel: string = '';
  tipoComercio: TipoComercio = 'FARMACIA';
  cidadeId?: number;
}
