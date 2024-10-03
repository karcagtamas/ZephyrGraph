export enum DecisionTableItem {
  True = 'True',
  False = 'False',
  NotUsed = 'NotUsed',
}

export interface DecisionTableRow {
  node: {
    id: string;
    displayName: string;
  };
  isEffect: boolean;
  items: (DecisionTableItem | null)[];
}

export interface DecisionTable {
  columns: string[];
  rows: DecisionTableRow[];
}
