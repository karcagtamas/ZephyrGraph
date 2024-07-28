import { Card } from '@mui/material';
import './MessageBoard.scss';
import MessageItem from './MessageItem';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';

const MessageBoard = () => {
  const messages = useSelector((state: RootState) => state.messages.messages);

  return (
    <Card className="board">
      {messages.map((msg) => (
        <MessageItem key={msg.id} message={msg} />
      ))}
    </Card>
  );
};

export default MessageBoard;
