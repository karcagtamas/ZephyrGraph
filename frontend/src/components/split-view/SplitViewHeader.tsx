import { Info, Schema, Sync } from '@mui/icons-material';
import { Card, Divider, IconButton } from '@mui/material';
import { FC } from 'react';
import './SplitViewHeader.scss';

type Props = {
  isGraphToggled: boolean;
  onGraphToggle: () => void;
  onExecute: () => void;
  isBottomToggled: boolean;
  onBottomToggle: () => void;
};

const SplitViewHeader: FC<Props> = (props: Props) => {
  return (
    <Card className="header">
      <IconButton>
        <Sync />
      </IconButton>
      <Divider />
      <IconButton
        onClick={() => props.onGraphToggle()}
        color={props.isGraphToggled ? 'primary' : 'default'}
      >
        <Schema />
      </IconButton>
      <IconButton
        onClick={() => props.onBottomToggle()}
        color={props.isBottomToggled ? 'warning' : 'default'}
      >
        <Info />
      </IconButton>
    </Card>
  );
};

export default SplitViewHeader;
