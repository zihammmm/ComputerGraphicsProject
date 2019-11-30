package reslover;

import expections.ParameterError;
import operation.primitiveCommand.*;
import operation.operationCommand.*;
import operation.toolCommand.*;



public interface ResloverMethod {
    DrawLine drawLine(String[] cmd);

    ResetCanvas resetCanvas(String[] cmd) throws ParameterError;

    SaveCanvas saveCanvas(String[] cmd);

    Rotate rotate(String[] cmd) throws ParameterError;

    Scale scale(String[] cmd) throws ParameterError;

    Clip clip(String[] cmd) throws ParameterError;

    Translate translate(String[] cmd) throws ParameterError;

    SetColor setColor(String[] cmd) throws ParameterError;

    DrawPolygon drawPolygon(String[] cmd);

    DrawEllipse drawEllipse(String[] cmd);

    DrawCurve drawCurve(String[] cmd);
}
