export interface LogicalItem {
  key: string;
  definitions: string[];
}

export interface LogicalModel {
  final: LogicalItem;
  prevSteps: LogicalItem[];
}
