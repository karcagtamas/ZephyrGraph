import { Edge, Node, ReactFlowProvider } from 'reactflow';
import { GraphModel, toFlow } from '../../models/graph.model';
import GraphContent from './GraphContent';
import './Graph.scss';
import { useEffect, useState } from 'react';

type Props = {
  model: GraphModel;
};

type State = {
  nodes: Node[];
  edges: Edge[];
};

const Graph: React.FC<Props> = (props: Props) => {
  const [state, setState] = useState<State>({ nodes: [], edges: [] });

  useEffect(() => {
    const [nodes, edges] = toFlow(props.model);
    console.log(nodes, edges);

    setState({ nodes, edges });
  }, [props.model]);

  return (
    <div className="graph-frame">
      <ReactFlowProvider>
        <GraphContent nodes={state.nodes} edges={state.edges} />
      </ReactFlowProvider>
    </div>
  );
};

export default Graph;
