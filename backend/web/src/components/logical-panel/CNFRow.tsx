import './CNFRow.scss';

type Props = {
  index: number;
  definition: string;
};

const CNFRow: React.FC<Props> = (props: Props) => {
  return (
    <div className="logical-row">
      <strong>{props.index + 1}. Rule:</strong>
      <code>{props.definition}</code>
    </div>
  );
};

export default CNFRow;
