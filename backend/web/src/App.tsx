import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.scss';
import SplitView from './components/split-view/SplitView';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <SplitView />
    </QueryClientProvider>
  );
}

export default App;
