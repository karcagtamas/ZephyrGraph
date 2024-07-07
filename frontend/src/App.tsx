import './App.scss';
import Graph from './components/graph/Graph';
import { Edge, GraphModel, Node } from './core/graph/graph.model';
import { CauseMeta, EffectMeta } from './core/graph/meta.model';

function App() {
  const model: GraphModel = {
    nodes: [
      new Node(new CauseMeta('c1', 'C1')),
      new Node(new CauseMeta('c2', 'C2')),
      new Node(new EffectMeta('e1', 'E1')),
    ],
    edges: [
      new Edge(new CauseMeta('c1', 'C1'), new CauseMeta('e1', 'E1')),
      new Edge(new CauseMeta('c2', 'C2'), new CauseMeta('e1', 'E1')),
    ],
  };

  return (
    <div className="main">
      <Graph model={model}></Graph>
    </div>
  );
}

export default App;
