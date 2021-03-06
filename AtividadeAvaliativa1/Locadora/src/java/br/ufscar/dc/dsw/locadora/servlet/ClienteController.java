/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.dsw.locadora.servlet;

import br.ufscar.dc.dsw.locadora.bean.Cliente;
import br.ufscar.dc.dsw.locadora.dao.ClienteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jackson Victor
 */
public class ClienteController extends HttpServlet {

    private ClienteDAO dao;

    @Override
    public void init() {
        dao = new ClienteDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String action = (String) request.getParameter("action");
        System.out.println("ACAO = " + action);
        try {
            if (action == null) {
                lista(request, response);
            } else if (action.equals("cadastro")) {
                apresentaFormCadastro(request, response);
            } else if (action.equals("insercao")) {
                insere(request, response);
            } else if (action.equals("remocao")) {
                remove(request, response);
            } else if (action.equals("edicao")) {
                apresentaFormEdicao(request, response);
            } else if (action.equals("atualizacao")) {
                atualize(request, response);
            } else {
                lista(request, response);
            }

        } catch (IOException ex) {
            Logger.getLogger(LocadoraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> listaClientes = dao.getAll();
        request.setAttribute("listaClientes", listaClientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/listaCliente.jsp");
        dispatcher.forward(request, response);
    }

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/formularioCliente.jsp");
        dispatcher.forward(request, response);
    }

    private void apresentaFormEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cliente = dao.get(request.getParameter("cpf"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/formularioCliente.jsp");
        request.setAttribute("cliente", cliente);
        dispatcher.forward(request, response);

    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String dataNascimento = request.getParameter("dataNascimento");
        //char sexo = request.getParameter("sexo").charAt(0);
        // boolean ativo = Boolean.getBoolean(request.getParameter("ativo"));
        Cliente cliente = new Cliente(cpf, nome, email, senha, telefone, dataNascimento, 'M', true);
        dao.insert(cliente);
        response.sendRedirect("listaCliente");
    }

    private void atualize(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String dataNascimento = request.getParameter("dataNascimento");
        char sexo = request.getParameter("sexo").charAt(0);
        boolean ativo = Boolean.getBoolean(request.getParameter("ativo"));
        Cliente cliente = new Cliente(cpf, nome, email, senha, telefone, dataNascimento, sexo, ativo);
        dao.update(cliente);
        response.sendRedirect("listaCliente");
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cpf = request.getParameter("cpf");
        dao.delete(cpf);
        response.sendRedirect("listaCliente");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
