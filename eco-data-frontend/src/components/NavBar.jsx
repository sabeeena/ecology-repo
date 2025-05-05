import {Navbar,Nav,Container} from 'react-bootstrap';
import {Link,useNavigate} from 'react-router-dom';

export default function NavBar(){
    const navigate = useNavigate();
    const token = localStorage.getItem('token');
    const role  = localStorage.getItem('role');

    const logout = () => {
        localStorage.clear();
        navigate('/login');
    };

    return (
        <Navbar variant="dark" expand="sm">
            <Container>
                <Navbar.Brand as={Link} to="/">EcoData</Navbar.Brand>
                <Nav className="ms-auto">
                    <Nav.Link as={Link} to="/">Дашборд</Nav.Link>

                    {!token && (
                        <>
                            <Nav.Link as={Link} to="/login">Вход</Nav.Link>
                            <Nav.Link as={Link} to="/register">Регистрация</Nav.Link>
                        </>
                    )}

                    {token && (
                        <>
                            <Nav.Link as={Link} to="/profile">Профиль</Nav.Link>
                            {role === 'ADMIN' && <Nav.Link as={Link} to="/admin">Админ</Nav.Link>}
                            <Nav.Link onClick={logout}>Выход</Nav.Link>
                        </>
                    )}
                </Nav>
            </Container>
        </Navbar>
    );
}
