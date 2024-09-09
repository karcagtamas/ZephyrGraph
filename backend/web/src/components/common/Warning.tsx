import { WarningOutlined } from '@mui/icons-material';
import './Warning.scss';
import { colors } from '@mui/material';

type Props = {
  content: string;
};

const Warning: React.FC<Props> = (props: Props) => {
  return (
    <div className="warning-box" style={{ color: colors.orange[900] }}>
      <WarningOutlined color="warning" />
      <div>{props.content}</div>
    </div>
  );
};

export default Warning;
