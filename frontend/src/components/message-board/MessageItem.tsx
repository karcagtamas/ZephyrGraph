import { FC } from 'react';
import { Message, MessageType } from '../../models/message';
import './MessageItem.scss';
import { Error, Info, Sync, Warning } from '@mui/icons-material';
import Spacer from '../common/Spacer';
import { Divider } from '@mui/material';

const getItemIcon = (type: MessageType) => {
  if (type === MessageType.EXECUTE) {
    return <Sync color="primary" />;
  } else if (type === MessageType.ERROR) {
    return <Error color="error" />;
  } else if (type === MessageType.WARNING) {
    return <Warning color="warning" />;
  }

  return <Info color="primary" />;
};

type Props = {
  message: Message;
};

const MessageItem: FC<Props> = (props: Props) => {
  return (
    <>
      <div className="item">
        {getItemIcon(props.message.type)}
        {props.message.content}
        <Spacer />
        {props.message.date.toDateString()}
      </div>
      <Divider />
    </>
  );
};

export default MessageItem;
