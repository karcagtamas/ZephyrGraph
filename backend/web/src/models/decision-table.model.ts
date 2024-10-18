export enum DecisionTableItem {
  True = 'True',
  False = 'False',
  NotUsed = 'NotUsed',
}

export interface DecisionTableRow {
  displayName: string;
  items: (DecisionTableItem | null)[];
}

export interface DecisionTable {
  columns: string[];
  rows: DecisionTableRow[];
}
