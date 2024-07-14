import Dagre from '@dagrejs/dagre';
import ReactFlow, {
  Background,
  BackgroundVariant,
  Controls,
  Edge,
  Node,
  MiniMap,
  useReactFlow,
  useNodesState,
  useEdgesState,
  Position,
  ConnectionLineType,
} from 'reactflow';
import { useCallback, useEffect, useState } from 'react';
import 'reactflow/dist/style.css';

type Props = {
  nodes: Node[];
  edges: Edge[];
};

const dagreGraph = new Dagre.graphlib.Graph();
dagreGraph.setDefaultEdgeLabel(() => ({}));

const nodeWidth = 172;
const nodeHeight = 36;

const getLayoutedElements = (
  nodes: Node[],
  edges: Edge[],
  direction = 'LR'
) => {
  const isHorizontal = direction === 'LR';

  dagreGraph.setGraph({ rankdir: direction });

  nodes.forEach((node) =>
    dagreGraph.setNode(node.id, { width: nodeWidth, height: nodeHeight })
  );

  edges.forEach((edge) => dagreGraph.setEdge(edge.source, edge.target));

  Dagre.layout(dagreGraph);

  return {
    nodes: nodes.map((node) => {
      const nodeWithPosition = dagreGraph.node(node.id);
      node.targetPosition = isHorizontal ? Position.Left : Position.Top;
      node.sourcePosition = isHorizontal ? Position.Right : Position.Bottom;

      node.position = {
        x: nodeWithPosition.x - nodeWidth / 2,
        y: nodeWithPosition.y - nodeHeight / 2,
      };

      return { ...node };
    }),
    edges,
  };
};

const GraphContent: React.FC<Props> = (props: Props) => {
  const { fitView } = useReactFlow();
  const [nodes, setNodes, onNodesChange] = useNodesState(props.nodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(props.edges);
  const [loaded, setLoaded] = useState(false);

  const setLayout = useCallback(() => {
    const layouted = getLayoutedElements(nodes, edges, 'LR');

    setNodes([...layouted.nodes]);
    setEdges([...layouted.edges]);

    window.requestAnimationFrame(() => {
      fitView();
    });
  }, [nodes, edges, setEdges, setNodes, fitView]);

  useEffect(() => {
    if (!loaded) {
      setLayout();
      setLoaded(true);
    }
  }, [setLayout, loaded]);

  return (
    <ReactFlow
      nodes={nodes}
      edges={edges}
      onNodesChange={onNodesChange}
      onEdgesChange={onEdgesChange}
      fitView
      attributionPosition="top-left"
      connectionLineType={ConnectionLineType.Straight}
      nodesConnectable={false}
      nodesFocusable={false}
      nodesDraggable={false}
      selectNodesOnDrag={false}
      elevateNodesOnSelect={false}
      elevateEdgesOnSelect={false}
    >
      <MiniMap />
      <Background variant={BackgroundVariant.Dots} gap={12} size={1} />
      <Controls position="bottom-left" />
    </ReactFlow>
  );
};

export default GraphContent;
