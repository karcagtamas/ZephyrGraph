import { Info, Schema, Sync } from '@mui/icons-material';
import { Card, Divider, IconButton } from '@mui/material';
import { FC } from 'react';
import './SplitViewHeader.scss';
import Warning from '../common/Warning';

type Props = {
  isGraphToggled: boolean;
  onGraphToggle: () => void;
  onExecute: () => void;
  isBottomToggled: boolean;
  onBottomToggle: () => void;
  warning?: string;
};

const SplitViewHeader: FC<Props> = (props: Props) => {
  return (
    <Card className="header">
      <div className="title">Cause-Effect Graph</div>
      <span className="spacer"></span>
      {props.warning ? <Warning content={props.warning} /> : <></>}
      <span className="spacer"></span>
      <IconButton onClick={() => props.onExecute()}>
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
