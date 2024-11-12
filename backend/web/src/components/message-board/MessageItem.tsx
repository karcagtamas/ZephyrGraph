import { FC, useState } from 'react';
import { Message, MessageType } from '../../models/message';
import './MessageItem.scss';
import {
  Close,
  Error,
  Info,
  OpenInFull,
  Sync,
  Warning,
} from '@mui/icons-material';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  ListItem,
  ListItemIcon,
  ListItemSecondaryAction,
  ListItemText,
  Typography,
} from '@mui/material';
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
  const [isOpen, setIsOpen] = useState(false);
  const formattedDate = localDateTimeConverter.toString(props.message.date);

  const toggleIsOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <ListItem divider>
      <ListItemIcon>{getItemIcon(props.message.type)}</ListItemIcon>
      <ListItemText>
        {formattedDate} - {props.message.content}
      </ListItemText>

      <Dialog open={isOpen} onClose={toggleIsOpen} maxWidth="xl">
        <DialogTitle color="primary">Message details</DialogTitle>
        <IconButton
          aria-label="close"
          onClick={toggleIsOpen}
          sx={() => ({
            position: 'absolute',
            right: 8,
            top: 8,
          })}
          color="primary"
        >
          <Close />
        </IconButton>
        <DialogContent dividers>
          <Typography gutterBottom>{props.message.content}</Typography>
          <pre>{props.message.details}</pre>
        </DialogContent>
        <DialogActions>
          <Button variant="outlined" color="secondary" onClick={toggleIsOpen}>
            Close
          </Button>
        </DialogActions>
      </Dialog>
      {props.message.details ? (
        <ListItemSecondaryAction>
          <IconButton color="secondary" onClick={toggleIsOpen}>
            <OpenInFull />
          </IconButton>
        </ListItemSecondaryAction>
      ) : (
        <></>
      )}
    </ListItem>
  );
};

export default MessageItem;
