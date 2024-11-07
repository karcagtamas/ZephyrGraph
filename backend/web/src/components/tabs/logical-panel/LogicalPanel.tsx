import { useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import './LogicalPanel.scss';
import { LogicalItem } from '../../../models/logical-graph.model';
import { Paper, Typography } from '@mui/material';
import { useState } from 'react';
import ExpandButton from '../../common/ExpandButton';

const keyParser = (key: string) => {
  switch (key) {
    case 'init':
      return 'Initalization';
    case 'negation-inward-mover':
      return 'Negation Inward Move';
    case 'cnf':
      return 'CNF';
    case 'dnf':
      return 'DNF';
    case 'optimizer':
      return 'Optimizer';
    case 'pre-optimizer':
      return 'Pre-Optimizer';
    case 'binary-collapser':
      return 'Binary Collapser';
    case 'opposition-eliminator':
      return 'Opposition Eliminator';
  }

  return `Unkown key (${key})`;
};

type ConversionBlockProps = {
  item: LogicalItem;
};

const ConversionBlock: React.FC<ConversionBlockProps> = (
  props: ConversionBlockProps
) => {
  const [isExpanded, setIsExpanded] = useState(true);
  const { key, definitions } = props.item;

  const toggleIsExpanded = () => {
    setIsExpanded(!isExpanded);
  };

  return (
    <Paper className="panel-block">
      <Typography variant="h6" color="primary" className="block-title">
        {keyParser(key)}
      </Typography>
      <div className="expand">
        <ExpandButton
          isExpanded={isExpanded}
          toggle={toggleIsExpanded}
          invert={false}
        />
      </div>
      {isExpanded ? (
        definitions.map((def, idx) => (
          <Typography variant="body2" key={idx} className="definition-row">
            {def}
          </Typography>
        ))
      ) : (
        <></>
      )}
    </Paper>
  );
};

const LogicalPanel = () => {
  const logicalState = useSelector((state: RootState) => state.logical);
  const items = [logicalState.final, ...[...logicalState.prevSteps].reverse()];

  return (
    <div className="panel">
      {items.map((item) => (
        <ConversionBlock key={item.key} item={item}></ConversionBlock>
      ))}
    </div>
  );
};

export default LogicalPanel;
