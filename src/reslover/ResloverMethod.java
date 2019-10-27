package reslover;

import expections.ParameterError;
import operation.primitiveCommand.*;
import operation.operationCommand.*;
import operation.toolCommand.*;



public interface ResloverMethod {
    DrawLine drawLine(String[] cmd);

    ResetCanvas resetCanvas(String[] cmd) throws ParameterError;

    SaveCanvas saveCanvas(String[] cmd);

    Rotate rotate(String[] cmd);

    Scale scale(String[] cmd);

    Clip clip(String[] cmd);

    Translate translate(String[] cmd);

    SetColor setColor(String[] cmd) throws ParameterError;

    DrawPolygon drawPolygon(String[] cmd);

    DrawEllipse drawEllipse(String[] cmd);

    DrawCurve drawCurve(String[] cmd);
}
