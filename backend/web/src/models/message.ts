import { LocalDateTime, localDateTimeConverter } from '../core/date.helper';

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
  date: LocalDateTime;
  details?: string;
}

export const message = (
  content: string,
  type: MessageType,
  details: string | undefined = undefined
): Message => {
  return {
    id: new Date().toISOString(),
    content,
    type,
    date: localDateTimeConverter.to(new Date()),
    details,
  };
};
