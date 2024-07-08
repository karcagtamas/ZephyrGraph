export enum MessageType {
  EXECUTE,
  ERROR,
  WARNING,
  INFO,
}

export interface Message {
  id: string;
  type: MessageType;
  content: string;
  date: Date;
}
