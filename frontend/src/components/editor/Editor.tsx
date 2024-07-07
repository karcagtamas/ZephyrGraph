import { TextareaAutosize } from '@mui/material';
import './Editor.scss';

const Editor = () => {
  return (
    <div className="editor">
      <TextareaAutosize className="field" />
    </div>
  );
};

export default Editor;
