@Service
public class AccessControlService {
 
  @Secured("ROLE_USER")
  public String secureMethod() {
    return "method level ";
  }
 
}
