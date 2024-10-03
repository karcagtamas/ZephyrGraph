import { useSelector } from 'react-redux';
import Graph from '../graph/Graph';
import { RootState } from '../../../store/store';
import EffectNode from './nodes/EffectNode';
import CauseNode from './nodes/CauseNode';
import ActionNode from './nodes/ActionNode';

const nodeTypes = {
  CauseNode,
  EffectNode,
  ActionNode,
};

const CauseEffectGraph = () => {
  const model = useSelector((state: RootState) => state.graph.model);

  if (!model) {
    return <div>No valid graph.</div>;
  }

  return <Graph model={model} nodeTypes={nodeTypes} />;
};

export default CauseEffectGraph;
