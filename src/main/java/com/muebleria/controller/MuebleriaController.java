package com.muebleria.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.runtime.SwitchBootstraps;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.muebleria.repository.ITipoUsuarioRepository;
import com.muebleria.repository.IUsuariosRepository;
import com.muebleria.model.Usuario;
import com.muebleria.model.Boleta;
import com.muebleria.model.Clientes;
import com.muebleria.model.Detalleboleta;
import com.muebleria.model.Empleados;
import com.muebleria.model.Productos;
import com.muebleria.model.Proveedor;
import com.muebleria.repository.iClienteRepository;
import com.muebleria.repository.iDetalleboletaRepository;
import com.muebleria.repository.iEmpleadoRepository;
import com.muebleria.repository.iProductoRepository;
import com.muebleria.repository.iProveedorRepository;
import com.muebleria.repository.iPuestoRepository;
import com.muebleria.repository.iTipoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@AllArgsConstructor
public class MuebleriaController {
	// llamar al repository -> usando objetos
			
			private  iClienteRepository repoCli;
			private  iEmpleadoRepository repoEmp;
			private  iPuestoRepository repoPues;
			private  iProveedorRepository repoProv;
			private  iProductoRepository repoProd;
			private  iTipoRepository repoTip;
			private ITipoUsuarioRepository repoTipoUsua;
			private IUsuariosRepository repoUsua; 
			private iDetalleboletaRepository repoDetboleta; 
			private DataSource dataSource; // javax.sql 
			private ResourceLoader resourceLoader; // core.io

		/*	@GetMapping("/cargarCrearCuenta")
			public String abrirpagCrearCuenta(Model model) {
				model.addAttribute("lstTipoUsuario", repoTipoUsua.findAll());
				model.addAttribute("usuarios", new Usuario());
				return "CrearCuenta";
			}*/
			
			@GetMapping("/cargarpruebapdf")
			public String abrirpaginapruebapdf(Model model) {
				List<Proveedor> proveedor = repoProv.findAll();
				model.addAttribute("lstProveedores", repoProv.findAll());
						model.addAttribute("proveedores", new Proveedor());
				return "pruebapdf";
			}
			   @GetMapping("/abrir-pdf")
			    public String abrirPdf(Model model) {
					List<Proveedor> proveedor = repoProv.findAll();
					model.addAttribute("lstProveedores", repoProv.findAll());
							model.addAttribute("proveedores", new Proveedor());
			        return "redirect:/reporte/reporte_proveedor.pdf";
			    }
			
		/*	@GetMapping("/cargarProductos")
			public String abrirpagProducto(Model model, HttpSession session) {
				Usuario usuario = (Usuario) session.getAttribute("u");
			    if (usuario != null) {
			       String nombreUsuario = usuario.getUsuario();

			        model.addAttribute("cliente", new Clientes());
			        model.addAttribute("usuario", nombreUsuario);

			        return "Producto";
			    } else {
			        
			        return "Login";
			    }
			}*/
			

			
		@GetMapping("/cargarMantenedores")
		public String abrirpagMantenimiento(Model model, HttpSession session) {
			Usuario usuario = (Usuario) session.getAttribute("u");
		    if (usuario != null) {
		       String nombreUsuario = usuario.getUsuario();

		        model.addAttribute("cliente", new Clientes());
		        model.addAttribute("usuario", nombreUsuario);

		        return "Mantenedores";
		    } else {
		        
		        return "redirect:/";
		    }
		}
		@GetMapping("/cargarReportes")
		public String abrirpagReporte(Model model, HttpSession session) {
			
			Usuario usuario = (Usuario) session.getAttribute("u");
		    if (usuario != null) {
		       String nombreUsuario = usuario.getUsuario();

		        model.addAttribute("cliente", new Clientes());
		        model.addAttribute("usuario", nombreUsuario);

		        return "Reporte";
		    } else {
		        
		        return "redirect:/";
		    }
		}
		
		@PostMapping("/reporte")
		public String reporteSelect(@RequestParam(name="btn") String value, Model model) {
			
			if (value.equals("0")) {
		        return "redirect:/cargarReporteVenta";
		    } else if (value.equals("1")) {
		        return "redirect:/cargarReporteClientes";
		    } else if (value.equals("2")) {
		        return "redirect:/cargarReporteEmpleado";
		    } else if (value.equals("3")) {
		        return "redirect:/cargarReporteProveedores";
		    } else {
		        
		        return "redirect:/cargarReportes";
		    }
			
			
			
			
			
		}
		@GetMapping("/cargarReporteVenta")
		public String abrirReporteVenta(Model model) {
			
			model.addAttribute("lstVenta", repoDetboleta.findAll());
			model.addAttribute("venta", new Detalleboleta());
			return "ReporteVenta";
			
			
		}
		
		
		
		
		@GetMapping("/cargarReporteClientes")
		public String abrirReporteCliente(Model model) {
			List<Clientes> clientes = repoCli.findAll();
			model.addAttribute("lstCliente", repoCli.findAll());
			
					model.addAttribute("cliente", new Clientes());
			return "ReporteCliente";
		}
		@GetMapping("/cargarReporteProveedores")
		public String abrirReporteProveedor(Model model) {
			List<Proveedor> proveedor = repoProv.findAll();
			model.addAttribute("lstProveedores", repoProv.findAll());
					model.addAttribute("proveedores", new Proveedor());
			return "ReporteProveedor";
		}
		@GetMapping("/cargarReporteEmpleado")
		public String abrirReporteEmpleado(Model model) {
			List<Empleados> empleado = repoEmp.findAll();
			model.addAttribute("lstEmpleado", repoEmp.findAll());
			model.addAttribute("empleado", new Empleados());
			model.addAttribute("lstPuesto", repoPues.findAll());
			return "ReporteEmpleado";
		}
		
		@GetMapping("/cargarIndex")
		public String abrirIndex(Model model, HttpSession session) {
			
		    Usuario usuario = (Usuario) session.getAttribute("u");
		    List<Productos> producto = repoProd.findAll();
		    model.addAttribute("lstProducto", repoProd.findAll());
		    model.addAttribute("producto", new Productos());

		    if (usuario != null) {
		        String nombreUsuario = usuario.getUsuario();

		        model.addAttribute("cliente", new Clientes());
		        model.addAttribute("usuario", nombreUsuario);
		        model.addAttribute("u", usuario); 

		        return "Inicio";
		    } else {
		     
		        return "redirect:/";
		    }
		}
		@GetMapping("/cargarMantenedorCliente")
		public String abrirpagMantenedorCliente(Model model) {
			
					model.addAttribute("cliente", new Clientes());
			return "MantenedorCliente";
			
		}
		@GetMapping("/cargarMantenedorProveedores")
		public String abrirpagMantenedorProveedores(Model model) {
			
					model.addAttribute("proveedores", new Proveedor());
			return "MantenedorProveedores";
		}
		@GetMapping("/cargarMantenedorProductos")
		public String abrirpagMantenedorProductos(Model model , HttpSession session) {
			Usuario usuario = (Usuario) session.getAttribute("u");
			  model.addAttribute("lstTipo", repoTip.findAll());
				model.addAttribute("lstProve", repoProv.findAll());
				model.addAttribute("producto", new Productos());
					
		    if (usuario != null) {
			       String nombreUsuario = usuario.getUsuario();
			        model.addAttribute("cliente", new Clientes());
			        model.addAttribute("usuario", nombreUsuario);
			      
			        return "MantenedorProducto.html";
			    } else {
			        
			        return "Login";
			    }
		   
		}
		@GetMapping("/cargarMantenedorEmpleado")
		public String abrirpagMantenedorEmpleado(Model model) {
			model.addAttribute("lstPuesto", repoPues.findAll());
			
					model.addAttribute("empleado", new Empleados());
			return "MantenedorEmpleados";
		}
		@GetMapping("/cargarConsulta")
		public String abrirpagConsulta(Model model , HttpSession session) {
			
			Usuario usuario = (Usuario) session.getAttribute("u");
		    if (usuario != null) {
		       String nombreUsuario = usuario.getUsuario();

		        model.addAttribute("cliente", new Clientes());
		        model.addAttribute("usuario", nombreUsuario);
		        model.addAttribute("u", usuario); 

		        return "Consulta";
		    } else {
		       
		        return "redirect:/";
		    }
		    	
		}
		


		
		@GetMapping("/cargarConsultaCliente")
		public String abrirpagConsultaCliente(Model model) {
			 List<Clientes> clientes = repoCli.findAll();
			model.addAttribute("lstCliente", repoCli.findAll());
			
					model.addAttribute("cliente", new Clientes());
			return "ConsultaCliente";
		}
		@GetMapping("/cargarConsultaProveedores")
		public String abrirpagConsultaProveedor(Model model) {
			List<Proveedor> proveedor = repoProv.findAll();
			model.addAttribute("lstProveedores", repoProv.findAll());
		
					model.addAttribute("proveedores", new Proveedor());
			return "ConsultaProveedores";
		}
		@GetMapping("/cargarConsultaProductos")
		public String abrirpagConsultaProductos(Model model, HttpSession session) {
		    Usuario usuario = (Usuario) session.getAttribute("u");
		    List<Productos> producto = repoProd.findAll();
		    model.addAttribute("lstProducto", repoProd.findAll());
		    model.addAttribute("producto", new Productos());

		    if (usuario != null) {
		        String nombreUsuario = usuario.getUsuario();

		        model.addAttribute("cliente", new Clientes());
		        model.addAttribute("usuario", nombreUsuario);
		        model.addAttribute("u", usuario); 

		        return "ConsultaProducto";
		    } else {
		       
		        return "Login";
		    }
		}

	@GetMapping("/cargarConsultaEmpleado")
		public String abrirpagConsultaEmpleado(Model model) {
			List<Empleados> empleado = repoEmp.findAll();
			model.addAttribute("lstEmpleado", repoEmp.findAll());
			model.addAttribute("empleado", new Empleados());
			model.addAttribute("lstPuesto", repoPues.findAll());
			return "ConsultaEmpleado.html";
		}
		
		
		//controlador para grabar un cliente 
			@PostMapping("/cliente/grabar")
			public String guardarCliente(@ModelAttribute Clientes cliente, Model model) {
				model.addAttribute("cliente", new Clientes());
				//leer el obj de tipo producto
				System.out.println(cliente);
				// Para grabar
				try {
					repoCli.save(cliente); //El metodo save sirve para agregar o actualizar
					model.addAttribute("mensaje", "Proceso OK");
					model.addAttribute("clase", "alert alert-success");
					return "MantenedorCliente";
				} catch (Exception e) {
					model.addAttribute("mensaje", "Error al Procesar");
					model.addAttribute("clase", "alert alert-danger");
					return "MantenedorCliente";
				}
			}
			
			@GetMapping("/cargar/actualizar/{cod_cliente}")
		    public String abrirPagCli(Model model, @PathVariable("cod_cliente") int cod_cliente) {
		        Clientes cliente = repoCli.findById(cod_cliente).orElse(null);
		        model.addAttribute("cliente", cliente);
		        return "MantenedorCliente";
		    }
			
			@GetMapping("/cliente/eliminar/{cod_cliente}")
			public String eliminarCliente(Model model, @PathVariable("cod_cliente") int cod_cliente) {
				Clientes cliente = repoCli.findById(cod_cliente).orElse(null);
				 model.addAttribute("cliente", cliente);
			    try {
			        repoCli.deleteById(cod_cliente); // Elimina el cliente utilizando el repositorio
			        model.addAttribute("mensaje", "Cliente eliminado correctamente");
			        model.addAttribute("clase", "alert alert-success");
			    } catch (Exception e) {
			        model.addAttribute("mensaje", "Error al eliminar el cliente");
			        model.addAttribute("clase", "alert alert-danger");
			    }
			    
			    // Realiza cualquier redireccionamiento o carga adicional de datos necesarios
			    // y devuelve la vista correspondiente
			    return "redirect:/cargarConsultaCliente"; // Redirecciona a la página de consulta de clientes
			}
			
			@PostMapping("/cliente/buscar")
			public String buscarCliente(@RequestParam("nombre") String nombre, Model model) {
			    List<Clientes> resultados = repoCli.findByNombreContaining(nombre);
			    model.addAttribute("lstCliente", resultados);
			    return "ConsultaCliente";
			}
			
			
			
		//controlador para grabar un proveedor 
			@PostMapping("/proveedores/grabar")
			public String guardarProveedor(@ModelAttribute Proveedor proveedores, Model model) {
				model.addAttribute("proveedores", new Proveedor());
				//leer el obj de tipo producto
				System.out.println(proveedores);
				// Para grabar
				try {
					repoProv.save(proveedores); //El metodo save sirve para agregar o actualizar
					model.addAttribute("mensaje", "Proceso OK");
					model.addAttribute("clase", "alert alert-success");
					return "MantenedorProveedores";
				} catch (Exception e) {
					model.addAttribute("mensaje", "Error al Procesar");
					model.addAttribute("clase", "alert alert-danger");
					return "MantenedorProveedores";
				}
			}
			
			@GetMapping("/cargar/actualizarProv/{cod_provee}")
		    public String abrirPagProv(Model model, @PathVariable("cod_provee") int cod_provee) {
		        Proveedor proveedores = repoProv.findById(cod_provee).orElse(null);
		        model.addAttribute("proveedores", proveedores);
		        return "MantenedorProveedores";
		    }
			
			@PostMapping("/prov/buscar")
			public String buscarProvee(@RequestParam("razon") String razon, Model model) {
			    List<Proveedor> resultados = repoProv.findByRazonContaining(razon);
			    model.addAttribute("lstProveedores", resultados);
			    return "ConsultaProveedores";
			}
			
			@GetMapping("/prov/eliminar/{cod_provee}")
			public String eliminarProv(Model model, @PathVariable("cod_provee") int cod_provee) {
				Proveedor proveedores = repoProv.findById(cod_provee).orElse(null);
				 model.addAttribute("proveedores", proveedores);
			    try {
			        repoProv.deleteById(cod_provee); // Elimina el cliente utilizando el repositorio
			        model.addAttribute("mensaje", "Cliente eliminado correctamente");
			        model.addAttribute("clase", "alert alert-success");
			    } catch (Exception e) {
			        model.addAttribute("mensaje", "Error al eliminar el cliente");
			        model.addAttribute("clase", "alert alert-danger");
			    }
			    
			    // Realiza cualquier redireccionamiento o carga adicional de datos necesarios
			    // y devuelve la vista correspondiente
			    return "redirect:/cargarConsultaProveedores"; // Redirecciona a la página de consulta de clientes
			}
			
			
			//controlador para grabar un empleado 
			@PostMapping("/empleado/grabar")
			public String guardarEmpleado(@ModelAttribute Empleados empleado, Model model, @RequestParam (name="btn") String opcion , HttpServletResponse response) {
				model.addAttribute("empleado", new Empleados());
				System.out.println(empleado);
				switch (opcion) {
				case "reg":
					// Para grabar
					try {
						repoEmp.save(empleado); //El metodo save sirve para agregar o actualizar
						model.addAttribute("mensaje", "Proceso OK");
						model.addAttribute("clase", "alert alert-success");
						return "MantenedorEmpleados";
					} catch (Exception e) {
						model.addAttribute("mensaje", "Error al Procesar");
						model.addAttribute("clase", "alert alert-danger");
						
					}
					break;

				case "fil":
					response.setHeader("Content-Disposition", "inline;");
					response.setContentType("application/pdf");
					try {
						String ru = resourceLoader.getResource("classpath:reporte/reporte_empleado_filtro.jasper").getURI().getPath();
						HashMap parametro = new HashMap<>();
						parametro.put("puesto", empleado.getPuesto());
					
						JasperPrint jasperPrint = JasperFillManager.fillReport(ru,parametro, dataSource.getConnection());
						OutputStream outStream = response.getOutputStream();
						JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					break;
				}
				return null;
				
			}
			
			@GetMapping("/cargar/actualizarEmp/{codigo}")
		    public String abrirPagEmp(Model model, @PathVariable("codigo") int codigo) {
		        Empleados empleado = repoEmp.findById(codigo).orElse(null);
		        model.addAttribute("empleado", empleado);
		        model.addAttribute("lstPuesto", repoPues.findAll());
				return "MantenedorEmpleados";
		    }
			@GetMapping("/empleado/eliminar/{codigo}")
			public String eliminarEmp(Model model, @PathVariable("codigo") int codigo) {
				Empleados empleado = repoEmp.findById(codigo).orElse(null);
				 model.addAttribute("empleado", empleado);
			    try {
			        repoEmp.deleteById(codigo);
			        model.addAttribute("mensaje", "Cliente eliminado correctamente");
			        model.addAttribute("clase", "alert alert-success");
			    } catch (Exception e) {
			        model.addAttribute("mensaje", "Error al eliminar el cliente");
			        model.addAttribute("clase", "alert alert-danger");
			    }
			    
			    // Realiza cualquier redireccionamiento o carga adicional de datos necesarios
			    // y devuelve la vista correspondiente
			    return "redirect:/cargarConsultaEmpleado"; // Redirecciona a la página de consulta de empleados
			}
			@PostMapping("/empleado/buscar")
			public String buscarEmp(@RequestParam("nombre") String nombre, Model model) {
			    List<Empleados> resultados = repoEmp.findByNombreContaining(nombre);
			    model.addAttribute("lstEmpleado", resultados);
			    return "ConsultaEmpleado";
			}
			
			//controlador para grabar un producto 
			@PostMapping("/productos/grabar")
			public String guardarProducto(@ModelAttribute Productos productos, Model model, @RequestParam (name="btn") String opcion , HttpServletResponse response) {
				model.addAttribute("producto", new Productos());
				//leer el obj de tipo producto
				System.out.println(productos);
				switch (opcion) {
		
			
				case "reg":
					
					// Para grabar
					try {
						repoProd.save(productos); //El metodo save sirve para agregar o actualizar
						model.addAttribute("mensaje", "Proceso OK");
						model.addAttribute("clase", "alert alert-success");
						return "MantenedorProducto";
					} catch (Exception e) {
						model.addAttribute("mensaje", "Error al Procesar");
						model.addAttribute("clase", "alert alert-danger");
						
					}
					break;

				case "fil":
					response.setHeader("Content-Disposition", "inline;");
					response.setContentType("application/pdf");
					try {
						String ru = resourceLoader.getResource("classpath:reporte/reporte_producto_filtro.jasper").getURI().getPath();
						HashMap parametro = new HashMap<>();
						parametro.put("proveedores", productos.getCod_provee());
						parametro.put("tipo", productos.getIdtipo());
						JasperPrint jasperPrint = JasperFillManager.fillReport(ru,parametro, dataSource.getConnection());
						OutputStream outStream = response.getOutputStream();
						JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}
				return null;
			
			}
			@GetMapping("/cargar/actualizarProd/{cod_prod}")
		    public String abrirPagProd(Model model, @PathVariable("cod_prod") int cod_prod) {
		        Productos producto = repoProd.findById(cod_prod).orElse(null);
		        model.addAttribute("producto", producto);
		        model.addAttribute("lstTipo", repoTip.findAll());
				model.addAttribute("lstProve", repoProv.findAll());
		        return "MantenedorProducto";
		    }
			
			
			@GetMapping("/prod/eliminar/{cod_prod}")
			public String eliminarProd(Model model, @PathVariable("cod_prod") int cod_prod) {
				Productos producto = repoProd.findById(cod_prod).orElse(null);
				 model.addAttribute("producto", producto);
			    try {
			        repoProd.deleteById(cod_prod); // Elimina el producto utilizando el repositorio
			        model.addAttribute("mensaje", "Cliente eliminado correctamente");
			        model.addAttribute("clase", "alert alert-success");
			    } catch (Exception e) {
			        model.addAttribute("mensaje", "Error al eliminar el cliente");
			        model.addAttribute("clase", "alert alert-danger");
			    }
			 
			    return "redirect:/cargarConsultaProductos"; 
			} 
			
		
			        
		
		
			@PostMapping("/producto/buscar")
			public String buscarProducto(@RequestParam("descripcion") String descripcion, Model model, HttpSession session) {
				Usuario usuario = (Usuario) session.getAttribute("u");
			    List<Productos> producto = repoProd.findAll();
			    List<Productos> resultados = repoProd.findByDescripcionContaining(descripcion);
			    model.addAttribute("lstProducto", resultados);
			    
			    
			    if (usuario != null) {
			        String nombreUsuario = usuario.getUsuario();

			        model.addAttribute("cliente", new Clientes());
			        model.addAttribute("usuario", nombreUsuario);
			        model.addAttribute("u", usuario); // Agregamos el objeto 'u' al modelo

			        return "ConsultaProducto";
			    } else {
			        // Manejar el caso cuando el usuario no ha iniciado sesión
			        return "Login";
			    }
			}
			
			
			//Login
			@PostMapping("/login")
			public String procesoLogin( @RequestParam("txtUsuario") String usuario ,@RequestParam("txtClave") String clave,HttpSession session, Model model) {
				Usuario u = repoUsua.findByUsuario(usuario);
				if(u != null && u.getClave().equals(clave)) {
					session.setAttribute("u",u );
					model.addAttribute("u", u);
					
					return "redirect:/cargarIndex";
				}	
				else {
					model.addAttribute("error","cuenta no existente");
					return "redirect:/";
				}
			}
		/*	@PostMapping("/login")
			public String procesoLogin(@ModelAttribute Usuario usuario, HttpSession session, Model model) {
			    Usuario u = repoUsua.findByUsuario(usuario.getUsuario());
			    if (u != null && u.getClave().equals(usuario.getClave())) {
			        session.setAttribute("u", u);
			        model.addAttribute("u", u);
			        return "Inicio";
			    } else {
			        model.addAttribute("error", "Cuenta no existente");
			        return "Login";
			    }
			}*/

			// grabar usuario
			@PostMapping("/usuario/grabar")
			public String GrabarUsuario(@ModelAttribute("usuarios") Usuario usuario, Model model) {
				System.out.println(usuario);
				try {
					repoUsua.save(usuario);
					model.addAttribute("mensaje", "usuario registrado");
					model.addAttribute("clase", "alert alert-success");
				} catch (Exception e) {
					model.addAttribute("mensaje", "Error al Registrar");
					model.addAttribute("clase", "alert alert-danger");
				}
				model.addAttribute("lstTipoUsuario", repoTipoUsua.findAll());
				return "redirect:/";
			}
			//cerrar session
			
			@GetMapping("/CerrarSesion")
			public String cerrarSesion(HttpServletRequest request , HttpSession session) {
				session.invalidate();
				return "redirect:/";
			}
			
			//reportes
			 @GetMapping("/reporteventa")
			 public void generarPDFVenta(HttpServletResponse response) {
					response.setHeader("Content-Disposition", "inline;");
					response.setContentType("application/pdf");
					try {
						String ru = resourceLoader.getResource("classpath:reporte/reporte_venta.jasper").getURI().getPath();
						JasperPrint jasperPrint = JasperFillManager.fillReport(ru,null, dataSource.getConnection());
						OutputStream outStream = response.getOutputStream();
						JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
					} catch (Exception e) {
						e.printStackTrace();
					}
			 }
			
			
			@GetMapping("/reporteempleado")
			public void generarPDFEmpleado(HttpServletResponse response) {
				response.setHeader("Content-Disposition", "inline;");
				response.setContentType("application/pdf");
				try {
					String ru = resourceLoader.getResource("classpath:reporte/reporte_empleado.jasper").getURI().getPath();
					JasperPrint jasperPrint = JasperFillManager.fillReport(ru,null, dataSource.getConnection());
					OutputStream outStream = response.getOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			@GetMapping("/reportecliente")
			public void generarPDFCliente(HttpServletResponse response) {
				response.setHeader("Content-Disposition", "inline;");
				response.setContentType("application/pdf");
				try {
					String ru = resourceLoader.getResource("classpath:reporte/reporte_cliente.jasper").getURI().getPath();
					JasperPrint jasperPrint = JasperFillManager.fillReport(ru,null, dataSource.getConnection());
					OutputStream outStream = response.getOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@GetMapping("/reporteproducto")
			public void generarPDFProducto(HttpServletResponse response) {
				response.setHeader("Content-Disposition", "inline;");
				response.setContentType("application/pdf");
				try {
					String ru = resourceLoader.getResource("classpath:reporte/reporte_producto.jasper").getURI().getPath();
					JasperPrint jasperPrint = JasperFillManager.fillReport(ru,null, dataSource.getConnection());
					OutputStream outStream = response.getOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@GetMapping("/reporteproveedor")
			public void generarPDFProveedor(HttpServletResponse response) {
				response.setHeader("Content-Disposition", "inline;");
				response.setContentType("application/pdf");
				try {
					String ru = resourceLoader.getResource("classpath:reporte/reporte_proveedor.jasper").getURI().getPath();
					JasperPrint jasperPrint = JasperFillManager.fillReport(ru,null, dataSource.getConnection());
					OutputStream outStream = response.getOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			@GetMapping("/cargarProductos")
			public String mostrarProducto(Model model , HttpSession session) {
				
			    Usuario usuario = (Usuario) session.getAttribute("u");
			    List<Productos> producto = repoProd.findAll();
			    model.addAttribute("lstProducto", repoProd.findAll());
			    model.addAttribute("producto", new Productos());

			    if (usuario != null) {
			        String nombreUsuario = usuario.getUsuario();

			        model.addAttribute("cliente", new Clientes());
			        model.addAttribute("usuario", nombreUsuario);
			        model.addAttribute("u", usuario); 

			        return "Producto";
			    } else {
			        
			        return "redirect:/";
			    }
			}
			
			
			
			@GetMapping("/CargarFormularioProducto")
			public String cargarFormularioProducto(Model model ,HttpSession session) {
				 Usuario usuario = (Usuario) session.getAttribute("u");
			    Productos productosInSession = (Productos) session.getAttribute("producto");
			    if (productosInSession != null && usuario != null) {
			    	 String nombreUsuario = usuario.getUsuario();
			        model.addAttribute("producto", productosInSession);
			        model.addAttribute("usuario", nombreUsuario);
			        model.addAttribute("u", usuario); 
			        return "FormularioProducto";
			    } else {
			    	return "redirect:/";
			    }
			}
			
			@GetMapping("/cargarCarritoCompras")
			public String  cargarCarritoCompras(Model model, HttpSession session) {
				 Usuario usuario = (Usuario) session.getAttribute("u");
			    List<Detalleboleta> carro = (List<Detalleboleta>) session.getAttribute("carro");
			    Double subTotalVenta = (Double) session.getAttribute("subTotalVenta");
			    Integer cantArticulos = (Integer) session.getAttribute("cantArticulos");
			 
			    model.addAttribute("carro", carro);
			    model.addAttribute("subTotalVenta", subTotalVenta);
			    model.addAttribute("cantArticulos", cantArticulos);
			    if ( usuario != null) {
			    	 String nombreUsuario = usuario.getUsuario();
			    model.addAttribute("usuario", nombreUsuario);
		        model.addAttribute("u", usuario); 


			        return "CarritoCompras";
			    }else {
			    	return "redirect:/";
			    }
			}
			
			@PostMapping("/agregarProducto")
			public String agregarSeleccionado(@RequestParam("btnAccion") String accion, @RequestParam("cod_prod") int cod_prod , HttpSession session) {
				System.out.println("Entro en el controlador de AgregarSeleccionado");
				System.out.println("Accion: "+ accion);
				
				switch (accion){
					case "addProductos" :
						Productos productos = repoProd.findById(cod_prod).orElse(null);
						session.setAttribute("producto", productos);
						return "redirect:/CargarFormularioProducto";
					default:
						return "redirect:/cargarProductos";
				}
			}
			
			@PostMapping("/agregarCarrito")
			public String agregarCompraProducto(@RequestParam("cantidad") int cantidad, HttpSession session) {
				System.out.println("entro en el controlador de agregarCarrtio");
				 Productos productosInSession = (Productos) session.getAttribute("producto");
				 
				 Detalleboleta d = new Detalleboleta();
				 d.setCod_prod(productosInSession.getCod_prod());
				 d.setNomProd(productosInSession.getDescripcion());
				 d.setPreciovta(productosInSession.getPrecio());
				 d.setCantidad(cantidad);
				 d.setImporte(d.getCantidad() * d.getPreciovta());
				 
				 List<Detalleboleta> carro = (List<Detalleboleta>) session.getAttribute("carro");
				 if (carro == null) {
				        carro = new ArrayList<>();
				    }
				 Double subTotalVentaObj = (Double) session.getAttribute("subTotalVenta");
				 double subTotalVenta = (subTotalVentaObj != null) ? subTotalVentaObj.doubleValue() : 0.0;

				 Integer cantArticulosObj = (Integer) session.getAttribute("cantArticulos");
				 int cantArticulos = (cantArticulosObj != null) ? cantArticulosObj.intValue() : 0;

				 
				 carro.add(d);
				 
				 subTotalVenta += d.getImporte();
				 cantArticulos += d.getCantidad();
				 
				 session.setAttribute("carro", carro);
				 session.setAttribute("subTotalVenta", subTotalVenta);
				 session.setAttribute("cantArticulos", cantArticulos);
				 
				 return "redirect:/cargarCarritoCompras";
			}
			
			@PostMapping("/finalizarCompra")
			public String FinalizarCompra(Model model , HttpSession session) {
				System.out.println("entro al proceso de finalizar compra");
				Boleta b = new Boleta();
				Usuario u = (Usuario) session.getAttribute("u");
				
				if(u == null) {
					return "redirect:/";
				}
				
				b.setCodigo(u.getCodigo());
				ArrayList<Detalleboleta> carro = (ArrayList<Detalleboleta>) session.getAttribute("carro");
				int ok = repoDetboleta.realizarVenta(b, carro);
		        if (ok == 0) {
		            System.out.println("error al procesar la venta");
		            
		        } else {
		            System.out.println("venta realizada con exito");
		            session.setAttribute("mensaje", "Venta realizada exitosamente");
		            session.setAttribute("carro", new ArrayList<Detalleboleta>());
		            session.setAttribute("subTotalVenta", 0.0);
		            session.setAttribute("cantArticulos", 0);
		            
		        }
		        
		        return "redirect:/cargarCarritoCompras";
		        
			}
			
			
			@PostMapping("/eliminarCompra")
			public String EliminarCompra(@RequestParam("codigo") String codigo, HttpSession session) {
			
		        ArrayList<Detalleboleta> carro = (ArrayList<Detalleboleta>) session.getAttribute("carro");
		        double subTotalVenta = (double) session.getAttribute("subTotalVenta");
		        int cantArticulos = (int) session.getAttribute("cantArticulos");

		        for (Detalleboleta d : carro) {
		            if (d.getCod_prod() == Integer.parseInt(codigo)) {
		              
		                cantArticulos -= d.getCantidad();
		                subTotalVenta -= d.getImporte();
		                carro.remove(d);
		                break;
		            }
		        }

		        
		        session.setAttribute("carro", carro);
		        session.setAttribute("subTotalVenta", subTotalVenta);
		        session.setAttribute("cantArticulos", cantArticulos);

		       
		        return "redirect:/cargarCarritoCompras";
				
				
			}
			
			
			
			
			
}
