import { ClearAll, Close, Description, Terminal } from '@mui/icons-material';
import {
  Alert,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Typography,
} from '@mui/material';
import { useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { useState } from 'react';

type Props = {
  onExecute: () => void;
  onReset: () => void;
  isExecuting: boolean;
};

const EditorActions: React.FC<Props> = (props: Props) => {
  const warning = useSelector((state: RootState) => state.warning.message);
  const [isDocsOpen, setDocsIsOpen] = useState(false);

  const toggleDocs = () => {
    setDocsIsOpen(!isDocsOpen);
  };

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'row',
        justifyContent: 'flex-end',
        gap: '1rem',
      }}
    >
      <Button variant="outlined" color="secondary" onClick={toggleDocs}>
        <Description />
        Docs
      </Button>
      {warning ? <Alert severity="warning">{warning}</Alert> : <></>}
      <div style={{ flex: 1 }}></div>
      <Button
        variant="outlined"
        onClick={props.onReset}
        disabled={props.isExecuting}
      >
        <ClearAll />
        Reset
      </Button>
      <Button
        variant="contained"
        disabled={!warning || props.isExecuting}
        onClick={props.onExecute}
      >
        {props.isExecuting ? (
          <CircularProgress size={23} />
        ) : (
          <>
            <Terminal />
            <span>Execute</span>
          </>
        )}
      </Button>
      <Dialog open={isDocsOpen} onClose={toggleDocs} maxWidth="xl">
        <DialogTitle color="primary">Documentation</DialogTitle>
        <IconButton
          aria-label="close"
          onClick={toggleDocs}
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
          <Typography gutterBottom>In progress...</Typography>
        </DialogContent>
        <DialogActions>
          <Button variant="outlined" color="secondary" onClick={toggleDocs}>
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default EditorActions;
