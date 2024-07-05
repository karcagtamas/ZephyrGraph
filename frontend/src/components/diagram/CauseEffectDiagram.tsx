import createEngine, {
  CanvasWidget,
  DiagramModel,
} from '@projectstorm/react-diagrams';

const CauseEffectDiagram = () => {
  const engine = createEngine({ registerDefaultDeleteItemsAction: false });
  const model = new DiagramModel();

  engine.setModel(model);

  return <CanvasWidget engine={engine}></CanvasWidget>;
};

export default CauseEffectDiagram;
