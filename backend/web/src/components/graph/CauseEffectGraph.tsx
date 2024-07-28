import { useSelector } from 'react-redux';
import Graph from './Graph';
import { RootState } from '../../store/store';

const CauseEffectGraph = () => {
  const model = useSelector((state: RootState) => state.graph.model);

  if (!model) {
    return <div>No valid graph.</div>;
  }

  return <Graph model={model} />;
};

export default CauseEffectGraph;
