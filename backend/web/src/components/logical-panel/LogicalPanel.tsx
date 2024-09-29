import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';
import './LogicalPanel.scss';
import { LogicalItem } from '../../models/logical-graph.model';
import { Paper } from '@mui/material';

const keyParser = (key: string) => {
  switch (key) {
    case 'init':
      return 'Initalization';
    case 'negation-inward-mover':
      return 'Negation Inward Move';
    case 'cnf':
      return 'CNF';
  }

  return `Unkown key (${key})`;
};

type ConversionBlockProps = {
  item: LogicalItem;
};

const ConversionBlock: React.FC<ConversionBlockProps> = (
  props: ConversionBlockProps
) => {
  const { key, definitions } = props.item;
  return (
    <Paper className="panel-block">
      <div className="block-title">{keyParser(key)}</div>
      {definitions.map((def) => (
        <div className="definition-row">{def}</div>
      ))}
    </Paper>
  );
};

const LogicalPanel = () => {
  const logicalState = useSelector((state: RootState) => state.logical);
  const items = [logicalState.final, ...[...logicalState.prevSteps].reverse()];
  console.log(items);

  return (
    <div className="panel">
      {items.map((item) => (
        <ConversionBlock key={item.key} item={item}></ConversionBlock>
      ))}
    </div>
  );
};

export default LogicalPanel;
