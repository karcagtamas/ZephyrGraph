import { FC } from 'react';
import { Message, MessageType } from '../../models/message';
import './MessageItem.scss';
import { Error, Info, Sync, Warning } from '@mui/icons-material';
import Spacer from '../common/Spacer';
import { Divider } from '@mui/material';
import { localDateTimeConverter } from '../../core/date.helper';

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
  const formattedDate = localDateTimeConverter.toString(props.message.date);

  return (
    <>
      <div className="item">
        {getItemIcon(props.message.type)}
        <span className="message-content">{props.message.content}</span>
        <Spacer />
        <span className="message-date">{formattedDate}</span>
      </div>
      <Divider />
    </>
  );
};

export default MessageItem;
