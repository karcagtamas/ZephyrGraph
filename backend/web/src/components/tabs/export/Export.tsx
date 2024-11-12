import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { Button, Card, Typography } from '@mui/material';
import './Export.scss';
import Code from '../../common/Code';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { ContentCopy } from '@mui/icons-material';
import { addError, addInfo } from '../../../store/snackbarSlice';

const Export = () => {
  const content = useSelector((state: RootState) => state.export.content);
  const dispatch = useDispatch();

  const handleOnCopy = (_text: string, result: boolean) => {
    if (result) {
      dispatch(addInfo('GPT export has been copied to the clipboard'));
    } else {
      dispatch(addError('Error during the copying to the clipboard'));
    }
  };

  return (
    <Card className="export-block">
      <Typography variant="h6" color="primary">
        GPT Export
      </Typography>
      <Code>{content}</Code>
      <div className="actions">
        <CopyToClipboard text={content} onCopy={handleOnCopy}>
          <Button variant="outlined">
            <ContentCopy /> Copy to Clipboard
          </Button>
        </CopyToClipboard>
      </div>
    </Card>
  );
};

export default Export;
