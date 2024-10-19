import { Clear, DeveloperBoard, Info, Schema, Sync } from '@mui/icons-material';
import { Card, Divider, IconButton, Tooltip } from '@mui/material';
import { FC } from 'react';
import './SplitViewHeader.scss';
import Warning from '../common/Warning';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';

type Props = {
  isGraphToggled: boolean;
  onGraphToggle: () => void;
  isLogicalPanelToggled: boolean;
  onLogicalPanelToggle: () => void;
  isBottomToggled: boolean;
  onBottomToggle: () => void;
  onExecute: () => void;
  onReset: () => void;
};

const SplitViewHeader: FC<Props> = (props: Props) => {
  const warning = useSelector((state: RootState) => state.warning.message);

  return (
    <Card className="header">
      <div className="title">Cause-Effect Graph</div>
      <span className="spacer"></span>
      {warning ? <Warning content={warning} /> : <></>}
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
          props.isLogicalPanelToggled
            ? 'Hide logical panel'
            : 'Show logical panel'
        }
      >
        <IconButton
          onClick={() => props.onLogicalPanelToggle()}
          color={props.isLogicalPanelToggled ? 'primary' : 'default'}
        >
          <DeveloperBoard />
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
