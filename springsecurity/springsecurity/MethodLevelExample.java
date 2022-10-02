@Service public class MethodLevelExample {
 @Secured("ROLE_USER") public String secureMethod()
 { return "method level check done";
 } 
}
