import { Edge, Node, NodeTypes, ReactFlowProvider } from 'reactflow';
import { GraphModel, toFlow } from '../../../models/graph.model';
import GraphContent from './GraphContent';
import './Graph.scss';
import { useEffect, useState } from 'react';

type Props = {
  model: GraphModel;
  nodeTypes: NodeTypes;
};

type State = {
  nodes: Node[];
  edges: Edge[];
};

const Graph: React.FC<Props> = (props: Props) => {
  const [state, setState] = useState<State>({ nodes: [], edges: [] });

  useEffect(() => {
    const [nodes, edges] = toFlow(props.model);

    setState({ nodes, edges });
  }, [props.model]);

  return (
    <div className="graph-frame">
      <ReactFlowProvider>
        <GraphContent
          nodeTypes={props.nodeTypes}
          nodes={state.nodes}
          edges={state.edges}
        />
      </ReactFlowProvider>
    </div>
  );
};

export default Graph;
