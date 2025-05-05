import { Card } from 'react-bootstrap';
import LineChart from './LineChart';

export default function WeatherCard({ icon, title, unit, latest, points, color, updatedAt }) {
    return (
        <Card className="mb-4 shadow-sm position-relative">
            <Card.Body>
                <div style={{ position: 'absolute', top: '8px', right: '12px', fontSize: '0.75rem', color: '#6c757d' }}>
                    {updatedAt ? new Date(updatedAt).toLocaleString() : ''}
                </div>

                <h5>{icon} {title}</h5>
                <h3 className="fw-bold">{latest}{unit}</h3>
                <LineChart points={points} label={title} color={color} />
            </Card.Body>
        </Card>
    );
}