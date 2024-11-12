import { Typography } from '@mui/material';
import './Code.scss';

type Props = {
  children: React.ReactNode;
};

const Code: React.FC<Props> = (props: Props) => {
  return (
    <Typography variant="body2" className="code">
      {props.children}
    </Typography>
  );
};

export default Code;
