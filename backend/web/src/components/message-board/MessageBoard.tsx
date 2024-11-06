import { Card, Divider, IconButton, List, Typography } from '@mui/material';
import './MessageBoard.scss';
import MessageItem from './MessageItem';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';
import React, { useState } from 'react';
import { ExpandLess, ExpandMore } from '@mui/icons-material';

type Props = {
  caption: string;
};

const MessageBoard: React.FC<Props> = (props: Props) => {
  const messages = useSelector((state: RootState) => state.messages.messages);
  const [expanded, setExpanded] = useState(true);

  const toggleExpanded = () => {
    setExpanded(!expanded);
  };

  return (
    <Card className="board">
      <Typography
        className="mb-caption"
        variant="h6"
        component="div"
        color="primary"
      >
        {props.caption}
      </Typography>
      <div className="expand">
        <IconButton onClick={toggleExpanded}>
          {expanded ? (
            <ExpandMore color="secondary" />
          ) : (
            <ExpandLess color="secondary" />
          )}
        </IconButton>
      </div>
      <Divider />
      {expanded && messages.length ? (
        <List disablePadding className="mb-content">
          {messages.map((msg) => (
            <MessageItem key={msg.id} message={msg} />
          ))}
        </List>
      ) : (
        <></>
      )}
    </Card>
  );
};

export default MessageBoard;
