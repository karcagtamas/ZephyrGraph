import { FC } from 'react';
import { Message, MessageType } from '../../models/message';
import './MessageItem.scss';
import { Error, Info, Sync, Warning } from '@mui/icons-material';
import Spacer from '../common/Spacer';
import { Divider } from '@mui/material';
import { format } from 'date-fns';

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
  const formattedDate = format(props.message.date, 'yyyy-MM-dd HH:mm:ss');

  return (
    <>
      <div className="item">
        {getItemIcon(props.message.type)}
        {props.message.content}
        <Spacer />
        {formattedDate}
      </div>
      <Divider />
    </>
  );
};

export default MessageItem;
