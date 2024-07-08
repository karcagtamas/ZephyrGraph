import { FC } from 'react';
import { Message } from '../../models/message';
import { Card } from '@mui/material';
import './MessageBoard.scss';
import MessageItem from './MessageItem';

type Props = {
  messages: Message[];
};

const MessageBoard: FC<Props> = (props: Props) => {
  return (
    <Card className="board">
      {props.messages.map((msg) => (
        <MessageItem key={msg.id} message={msg} />
      ))}
    </Card>
  );
};

export default MessageBoard;
