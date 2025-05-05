import {BrowserRouter,Routes,Route,Navigate} from 'react-router-dom';
import {Container} from 'react-bootstrap';
import NavBar   from './components/NavBar';
import Login    from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Profile   from './pages/Profile';
import Admin     from './pages/AdminPage';

export default function App(){
    const token = !!localStorage.getItem('token');
    const role  =  localStorage.getItem('role');

    const Private = ({children,admin}) =>
        !token ? <Navigate to="/login"/> :
            admin && role!=='ADMIN' ? <Navigate to="/"/> :
                children;

    return (
        <BrowserRouter>
            <NavBar />
            <Container className="py-3">
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />

                    <Route path="/" element={<Dashboard />} />

                    <Route path="/profile" element={<Private><Profile /></Private>} />
                    <Route path="/admin" element={<Private admin><Admin /></Private>} />

                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </Container>
        </BrowserRouter>
    );
}
