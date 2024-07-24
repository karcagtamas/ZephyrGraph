import { Clear, Info, Schema, Sync } from '@mui/icons-material';
import { Card, Divider, IconButton, Tooltip } from '@mui/material';
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
  onReset: () => void;
};

const SplitViewHeader: FC<Props> = (props: Props) => {
  return (
    <Card className="header">
      <div className="title">Cause-Effect Graph</div>
      <span className="spacer"></span>
      {props.warning ? <Warning content={props.warning} /> : <></>}
      <span className="spacer"></span>
      <Tooltip title="Execute">
        <IconButton onClick={() => props.onExecute()}>
          <Sync />
        </IconButton>
      </Tooltip>
      <Tooltip title="Reset state">
        <IconButton onClick={() => props.onReset()}>
          <Clear />
        </IconButton>
      </Tooltip>
      <Divider />
      <Tooltip title={props.isGraphToggled ? 'Hide graph' : 'Show graph'}>
        <IconButton
          onClick={() => props.onGraphToggle()}
          color={props.isGraphToggled ? 'primary' : 'default'}
        >
          <Schema />
        </IconButton>
      </Tooltip>
      <Tooltip
        title={
          props.isBottomToggled ? 'Hide message board' : 'Show message board'
        }
      >
        <IconButton
          onClick={() => props.onBottomToggle()}
          color={props.isBottomToggled ? 'warning' : 'default'}
        >
          <Info />
        </IconButton>
      </Tooltip>
    </Card>
  );
};

export default SplitViewHeader;
