import { Divider } from '@mui/material';
import './NodeDescription.scss';

type Props = {
  content?: string;
};

const NodeDescription: React.FC<Props> = (props: Props) => {
  if (!props.content) {
    return <></>;
  }

  return (
    <>
      <Divider />
      <div className="description">{props.content}</div>
    </>
  );
};

export default NodeDescription;
