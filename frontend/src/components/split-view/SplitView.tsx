import { useState } from 'react';
import { Node, Edge, GraphModel } from '../../core/graph/graph.model';
import {
  Action,
  ActionMeta,
  CauseMeta,
  EffectMeta,
} from '../../core/graph/meta.model';
import Editor from '../editor/Editor';
import Graph from '../graph/Graph';
import './SplitView.scss';
import SplitViewHeader from './SplitViewHeader';

const SplitView = () => {
  const [isGraphVisible, setIsGraphVisible] = useState(false);
  const [isBottomVisible, setIsBottomVisible] = useState(false);

  const model: GraphModel = {
    nodes: [
      new Node(new CauseMeta('c1', 'C1')),
      new Node(new CauseMeta('c2', 'C2')),
      new Node(new CauseMeta('c3', 'C3')),
      new Node(new CauseMeta('c4', 'C4')),
      new Node(new CauseMeta('c5', 'C5')),
      new Node(new CauseMeta('c6', 'C6')),
      new Node(new CauseMeta('c7', 'C7')),
      new Node(new EffectMeta('e1', 'E1')),
      new Node(new EffectMeta('e2', 'E2')),
      new Node(new EffectMeta('e3', 'E3')),
      new Node(new ActionMeta('a1', 'AND', Action.AND)),
      new Node(new ActionMeta('a2', 'AND', Action.AND)),
      new Node(new ActionMeta('a3', 'OR', Action.OR)),
    ],
    edges: [
      new Edge(new CauseMeta('c1', 'C1'), new EffectMeta('e1', 'E1')),
      new Edge(new CauseMeta('c2', 'C2'), new EffectMeta('e1', 'E1')),
      new Edge(new CauseMeta('c1', 'C1'), new EffectMeta('e2', 'E2')),
      new Edge(
        new CauseMeta('c3', 'C3'),
        new ActionMeta('a1', 'AND', Action.AND)
      ),
      new Edge(
        new CauseMeta('c4', 'C4'),
        new ActionMeta('a1', 'AND', Action.AND)
      ),
      new Edge(
        new ActionMeta('a1', 'AND', Action.AND),
        new EffectMeta('e3', 'E3')
      ),
      new Edge(
        new CauseMeta('c5', 'C5'),
        new ActionMeta('a2', 'AND', Action.AND)
      ),
      new Edge(
        new CauseMeta('c6', 'C6'),
        new ActionMeta('a2', 'AND', Action.AND)
      ),
      new Edge(
        new CauseMeta('c7', 'C7'),
        new ActionMeta('a3', 'OR', Action.OR)
      ),
      new Edge(
        new ActionMeta('a2', 'AND', Action.AND),
        new ActionMeta('a3', 'OR', Action.OR)
      ),
      new Edge(
        new ActionMeta('a3', 'OR', Action.OR),
        new EffectMeta('e3', 'E3')
      ),
    ],
  };

  const handleGraphToggle = () => {
    setIsGraphVisible(!isGraphVisible);
  };

  const handleBottomToggle = () => {
    setIsBottomVisible(!isBottomVisible);
  };

  const handleExecute = () => {};

  return (
    <div className="frame">
      <SplitViewHeader
        isGraphToggled={isGraphVisible}
        onGraphToggle={handleGraphToggle}
        onExecute={handleExecute}
        isBottomToggled={isBottomVisible}
        onBottomToggle={handleBottomToggle}
      />
      <div className="content">
        <div className="part">
          <Editor />
        </div>
        {isGraphVisible ? (
          <div className="part">
            <Graph model={model} />
          </div>
        ) : (
          <></>
        )}
      </div>
      {isBottomVisible ? <div className="bottom"></div> : <></>}
    </div>
  );
};

export default SplitView;
