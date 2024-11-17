import './Scrollable.scss';

type Props = {
  children: React.ReactNode;
};

const Scrollable: React.FC<Props> = (props: Props) => {
  return <div className="scrollable">{props.children}</div>;
};

export default Scrollable;
