import { useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { Button, Card, Typography } from '@mui/material';
import './Export.scss';
import Code from '../../common/Code';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { ContentCopy } from '@mui/icons-material';

const Export = () => {
  const content = useSelector((state: RootState) => state.export.content);

  return (
    <Card className="export-block">
      <Typography variant="h6" color="primary">
        GPT Export
      </Typography>
      <Code>{content}</Code>
      <div className="actions">
        <CopyToClipboard text={content}>
          <Button variant="outlined">
            <ContentCopy /> Copy to Clipboard
          </Button>
        </CopyToClipboard>
      </div>
    </Card>
  );
};

export default Export;
