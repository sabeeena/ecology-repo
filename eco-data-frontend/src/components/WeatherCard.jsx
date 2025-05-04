import { Card } from 'react-bootstrap';
import LineChart from './LineChart';

export default function WeatherCard({icon, title, unit,
                                        latest, points, color}) {
    return (
        <Card className="mb-4 shadow-sm">
            <Card.Body>
                <Card.Title>
                    {icon}&nbsp;{title}&nbsp;
                    <span className="fw-bold">{latest}{unit}</span>
                </Card.Title>
                <LineChart points={points} label={title} color={color}/>
            </Card.Body>
        </Card>
    );
}