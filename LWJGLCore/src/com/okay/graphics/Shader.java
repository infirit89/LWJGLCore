package com.okay.graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import com.okay.core.Debug;
import com.okay.core.Insist;

import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Shader {

    private int shaderProgram;
    private boolean isShaderBound = false;

    public Shader(String vertexSource, String fragmentSource) 
    {
        createProgram(vertexSource, fragmentSource);
    }

    private String loadFile(String filePath) 
    {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));

            String line = reader.readLine();

            while(line != null) 
            {
                builder.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    private int createShader(String source, int type) 
    {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);

        GL20.glCompileShader(shaderID);

        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) 
        {
            Debug.LogInfo(GL20.glGetShaderInfoLog(shaderID));
            System.exit(-1);
        }

        return shaderID;
    }

    private void createProgram(String vertexSource, String fragmentSource) 
    {
        shaderProgram = GL20.glCreateProgram();
        int vertexShader = createShader(loadFile(vertexSource), GL20.GL_VERTEX_SHADER);
        int fragmentShader = createShader(loadFile(fragmentSource), GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);

        GL20.glLinkProgram(shaderProgram);
        GL20.glValidateProgram(shaderProgram);
    }

    public void bind() { if(!isShaderBound) { GL20.glUseProgram(shaderProgram); isShaderBound = true; } }
    public void unbind() { GL20.glUseProgram(0); isShaderBound = false; }

    private int getUniformLoc(String uniformName) 
    {
        int location = GL20.glGetUniformLocation(shaderProgram, uniformName);

        if(location == -1) 
        {
            Debug.LogError("Couldn't find the uniform: " + uniformName);
            System.exit(-1);
        }

        return location;
    }
    
    public<T> void upload1(String uniformName, T value) 
    {
        bind();

        if(value instanceof Integer)
            GL20.glUniform1i(getUniformLoc(uniformName), (int)value);
        else if(value instanceof Float)
            GL20.glUniform1f(getUniformLoc(uniformName), (float)value);
        else 
            Insist.Assert(!true, "Value 1 and value 2 are different types");
    }

    public<T> void upload2(String uniformName, T value1, T value2) 
    {
        bind();

        if(value1 instanceof Integer && value2 instanceof Integer)
            GL20.glUniform2i(getUniformLoc(uniformName), (int)value1, (int)value2);
        else if(value1 instanceof Float && value2 instanceof Integer)
            GL20.glUniform2f(getUniformLoc(uniformName), (float)value1, (float)value2);
        else
            Insist.Assert(!true, "Value 1 and value 2 are different types");
    }

    public<T> void upload3(String uniformName, T value1, T value2, T value3) 
    {
        bind();

        if(value1 instanceof Integer && value2 instanceof Integer && value3 instanceof Integer)
            GL20.glUniform3i(getUniformLoc(uniformName), (int)value1, (int)value2, (int)value3);
        else if(value1 instanceof Float && value2 instanceof Integer && value3 instanceof Float)
            GL20.glUniform3f(getUniformLoc(uniformName), (float)value1, (float)value2, (float) value3);
        else
            Insist.Assert(!true, "Value 1 and value 2 and value 3 are different types");
    }

    public<T> void upload4(String uniformName, T value1, T value2, T value3, T value4) 
    {
        bind();

        if(value1 instanceof Integer && value2 instanceof Integer && value3 instanceof Integer && value4 instanceof Integer)
            GL20.glUniform4i(getUniformLoc(uniformName), (int)value1, (int)value2, (int)value3, (int)value4);
        else if(value1 instanceof Float && value2 instanceof Integer && value3 instanceof Float && value4 instanceof Float)
            GL20.glUniform4f(getUniformLoc(uniformName), (float)value1, (float)value2, (float) value3, (float)value4);
        else
            Insist.Assert(!true, "Value 1 and value 2 and value 3 and value 4 are different types");
    }

    public void uploadVector2I(String uniformName, Vector2i value) 
    {
        bind();
        upload2(uniformName, value.x, value.y);
    }

    public void uploadVector2F(String uniformName, Vector2f value) 
    {
        bind();
        upload2(uniformName, value.x, value.y);
    }

    public void uploadVector3I(String uniformName, Vector3i value) 
    {
        bind();
        upload3(uniformName, value.x, value.y, value.z);
    }

    public void uploadVector3F(String uniformName, Vector3f value) 
    {
        bind();
        upload3(uniformName, value.x, value.y, value.z);
    }

    public void uploadVector4I(String uniformName, Vector4i value) 
    {
        bind();
        upload4(uniformName, value.x, value.y, value.z, value.w);
    }

    public void uploadVector4F(String uniformName, Vector4f value) 
    {
        bind();
        upload4(uniformName, value.x, value.y, value.z, value.w);
    }

    public void uploadMat4F(String uniformName, Matrix4f matrix) 
    {
        bind();

        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);

        GL20.glUniformMatrix4fv(getUniformLoc(uniformName), false, buffer);
    }
}
