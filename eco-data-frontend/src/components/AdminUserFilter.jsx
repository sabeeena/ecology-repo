import { useState } from 'react';
import { Row, Col, Form, Button } from 'react-bootstrap';

export default function AdminUserFilter({ roles, onChange, onSearch }) {
    const [f, setF] = useState({});

    const change = (k, v) => {
        const nf = { ...f, [k]: v };
        setF(nf);
        onChange(nf);
    };

    return (
        <Form className="mb-3">
            <Row className="g-2">
                <Col md={2}>
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        placeholder="Username"
                        value={f.username || ''}
                        onChange={e => change('username', e.target.value)}
                    />
                </Col>
                <Col md={2}>
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        placeholder="Email"
                        value={f.email || ''}
                        onChange={e => change('email', e.target.value)}
                    />
                </Col>
                <Col md={2}>
                    <Form.Label>Роль</Form.Label>
                    <Form.Select value={f.role || ''} onChange={e => change('role', e.target.value)}>
                        <option value="">Любая роль</option>
                        {roles.map(r => (
                            <option key={r.code} value={r.code}>
                                {r.name}
                            </option>
                        ))}
                    </Form.Select>
                </Col>
                <Col md={2}>
                    <Form.Label>Активность</Form.Label>
                    <Form.Select
                        value={f.isActive !== undefined ? String(f.isActive) : ''}
                        onChange={e =>
                            change('isActive', e.target.value !== '' ? e.target.value === 'true' : '')
                        }
                    >
                        <option value="">Любая</option>
                        <option value="true">Активные</option>
                        <option value="false">Заблок.</option>
                    </Form.Select>
                </Col>
                <Col md={2}>
                    <Form.Label>Создан от</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        value={f.createdAfter || ''}
                        onChange={e => change('createdAfter', e.target.value)}
                    />
                </Col>
                <Col md={2}>
                    <Form.Label>Создан до</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        value={f.createdBefore || ''}
                        onChange={e => change('createdBefore', e.target.value)}
                    />
                </Col>
            </Row>
            <Row className="g-2 mt-2">
                <Col md={10}>
                    <Form.Label>Фрагмент ФИО</Form.Label>
                    <Form.Control
                        placeholder="Фрагмент ФИО"
                        value={f.fullNameFragment || ''}
                        onChange={e => change('fullNameFragment', e.target.value)}
                    />
                </Col>
                <Col md={2} className="d-flex align-items-end">
                    <Button className="w-100 shadow-sm" variant="success" onClick={() => onSearch()}>
                        Найти
                    </Button>
                </Col>
            </Row>
        </Form>
    );
}
