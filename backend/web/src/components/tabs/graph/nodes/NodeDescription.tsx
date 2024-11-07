import { Divider, Typography } from '@mui/material';
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
      <div className="description">
        <Typography variant="body2">{props.content}</Typography>
      </div>
    </>
  );
};

export default NodeDescription;
