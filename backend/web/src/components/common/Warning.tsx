import { WarningOutlined } from '@mui/icons-material';
import './Warning.scss';
import { colors, Typography } from '@mui/material';

type Props = {
  content: string;
};

const Warning: React.FC<Props> = (props: Props) => {
  return (
    <div
      className="warning-box"
      style={{
        color: colors.orange[900],
        borderColor: colors.orange[900],
        backgroundColor: colors.orange[50],
      }}
    >
      <WarningOutlined color="warning" />
      <Typography variant="body2">{props.content}</Typography>
    </div>
  );
};

export default Warning;
