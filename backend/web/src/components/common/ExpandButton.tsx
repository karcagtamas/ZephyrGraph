import { ExpandLess, ExpandMore } from '@mui/icons-material';
import { IconButton } from '@mui/material';

type Props = {
  isExpanded: boolean;
  toggle: () => void;
  invert: boolean;
};

const ExpandButton: React.FC<Props> = (props: Props) => {
  return (
    <IconButton onClick={props.toggle}>
      {(Number(props.isExpanded) + Number(props.invert)) % 2 ? (
        <ExpandMore color="secondary" />
      ) : (
        <ExpandLess color="secondary" />
      )}
    </IconButton>
  );
};

export default ExpandButton;
