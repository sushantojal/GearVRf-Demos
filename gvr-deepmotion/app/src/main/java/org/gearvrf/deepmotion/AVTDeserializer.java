package org.gearvrf.deepmotion;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.utility.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sushant.o on 8/22/18.
 */

public class AVTDeserializer {

//    private static Map<String, tntLink> linkNameMap = new HashMap<String, tntLink>();
//
    private static Map<String, String> simulationRenderBoneMap = new HashMap<String, String>();
//
//    private static Map<String, Integer> dogControlParamsNameMap = createDogControlParams();
//
//    private static Map<String, Integer> createDogControlParams()
//    {
//        Map<String, Integer> dogControlParamsNameMap = new HashMap<>();
//
//        dogControlParamsNameMap.put("STRIDE_DURATION", tntDogControlParams.P_STRIDE_DURATION);
//        dogControlParamsNameMap.put("DESIRED_HEIGHT", tntDogControlParams.P_DESIRED_HEIGHT);
//        dogControlParamsNameMap.put("DESIRED_HEIGHT_TRAJ", tntDogControlParams.P_DESIRED_HEIGHT_TRAJ1);
//        dogControlParamsNameMap.put("VFORCE_KP_SAGITTAL", tntDogControlParams.P_VFORCE_KP_SAGITTAL);
//        dogControlParamsNameMap.put("VFORCE_KD_SAGITTAL", tntDogControlParams.P_VFORCE_KD_SAGITTAL);
//        dogControlParamsNameMap.put("VFORCE_KP_CORONAL", tntDogControlParams.P_VFORCE_KP_CORONAL);
//        dogControlParamsNameMap.put("VFORCE_KD_CORONAL", tntDogControlParams.P_VFORCE_KD_CORONAL);
//        dogControlParamsNameMap.put("VFORCE_KP_VERTICAL", tntDogControlParams.P_VFORCE_KP_VERTICAL);
//        dogControlParamsNameMap.put("VFORCE_KD_VERTICAL", tntDogControlParams.P_VFORCE_KD_VERTICAL);
//        dogControlParamsNameMap.put("VFORCE_FF_VERTICAL_WEIGHT_PERCENTAGE", tntDogControlParams.P_VFORCE_FF_VERTICAL_WEIGHT_PERCENTAGE);
//        dogControlParamsNameMap.put("BODYFRAME_TORQUE_KP", tntDogControlParams.P_BODYFRAME_TORQUE_KP);
//        dogControlParamsNameMap.put("BODYFRAME_TORQUE_KD", tntDogControlParams.P_BODYFRAME_TORQUE_KD);
//        dogControlParamsNameMap.put("BFRAME_LEAN_FORWARD", tntDogControlParams.P_BFRAME_LEAN_FORWARD);
//        dogControlParamsNameMap.put("BFRAME_LEAN_SIDEWAYS", tntDogControlParams.P_BFRAME_LEAN_SIDEWAYS);
//        dogControlParamsNameMap.put("BFRAME_LEAN_FORWARD_TRAJ", tntDogControlParams.P_BFRAME_LEAN_FORWARD_TRAJ1);
//        dogControlParamsNameMap.put("BFRAME_LEAN_SIDEWAYS_TRAJ", tntDogControlParams.P_BFRAME_LEAN_SIDEWAYS_TRAJ1);
//        dogControlParamsNameMap.put("SPINE_TWIST", tntDogControlParams.P_SPINE_TWIST);
//        dogControlParamsNameMap.put("SPINE_SLOUCH_FORWARD", tntDogControlParams.P_SPINE_SLOUCH_FORWARD);
//        dogControlParamsNameMap.put("SPINE_SLOUCH_SIDEWAYS", tntDogControlParams.P_SPINE_SLOUCH_SIDEWAYS);
//        dogControlParamsNameMap.put("SPINE_TWIST_TRAJ", tntDogControlParams.P_SPINE_TWIST_TRAJ1);
//        dogControlParamsNameMap.put("SPINE_SLOUCH_FORWARD_TRAJ", tntDogControlParams.P_SPINE_SLOUCH_FORWARD_TRAJ1);
//        dogControlParamsNameMap.put("SPINE_SLOUCH_SIDEWAYS_TRAJ", tntDogControlParams.P_SPINE_SLOUCH_SIDEWAYS_TRAJ1);
//        dogControlParamsNameMap.put("LEG_PLANE_ANGLE_FRONT", tntDogControlParams.P_LEG_PLANE_ANGLE_FRONT);
//        dogControlParamsNameMap.put("LEG_PLANE_ANGLE_REAR", tntDogControlParams.P_LEG_PLANE_ANGLE_REAR);
//        dogControlParamsNameMap.put("SWING_FOOT_HEIGHT_TRAJ", tntDogControlParams.P_SWING_FOOT_HEIGHT_TRAJ1);
//        dogControlParamsNameMap.put("SWING_WRIST_BEND", tntDogControlParams.P_SWING_WRIST_BEND1);
//        dogControlParamsNameMap.put("STANCE_WRIST_BEND", tntDogControlParams.P_STANCE_WRIST_BEND1);
//        dogControlParamsNameMap.put("STEP_WIDTH_FRONT", tntDogControlParams.P_STEP_WIDTH_FRONT);
//        dogControlParamsNameMap.put("STEP_WIDTH_REAR", tntDogControlParams.P_STEP_WIDTH_REAR);
//        dogControlParamsNameMap.put("SWING_FOOT_VFORCE_KP", tntDogControlParams.P_SWING_FOOT_VFORCE_KP);
//        dogControlParamsNameMap.put("SWING_FOOT_VFORCE_KD", tntDogControlParams.P_SWING_FOOT_VFORCE_KD);
//        dogControlParamsNameMap.put("SWING_FOOT_MAX_FORCE", tntDogControlParams.P_SWING_FOOT_MAX_FORCE);
//        dogControlParamsNameMap.put("FRONT_LEFT_LEG_SWING_START", tntDogControlParams.P_FRONT_LEFT_LEG_SWING_START);
//        dogControlParamsNameMap.put("FRONT_LEFT_LEG_SWING_END", tntDogControlParams.P_FRONT_LEFT_LEG_SWING_END);
//        dogControlParamsNameMap.put("FRONT_RIGHT_LEG_SWING_START", tntDogControlParams.P_FRONT_RIGHT_LEG_SWING_START);
//        dogControlParamsNameMap.put("FRONT_RIGHT_LEG_SWING_END", tntDogControlParams.P_FRONT_RIGHT_LEG_SWING_END);
//        dogControlParamsNameMap.put("REAR_LEFT_LEG_SWING_START", tntDogControlParams.P_REAR_LEFT_LEG_SWING_START);
//        dogControlParamsNameMap.put("REAR_LEFT_LEG_SWING_END", tntDogControlParams.P_REAR_LEFT_LEG_SWING_END);
//        dogControlParamsNameMap.put("REAR_RIGHT_LEG_SWING_START", tntDogControlParams.P_REAR_RIGHT_LEG_SWING_START);
//        dogControlParamsNameMap.put("REAR_RIGHT_LEG_SWING_END", tntDogControlParams.P_REAR_RIGHT_LEG_SWING_END);
//        dogControlParamsNameMap.put("STEP_TARGET_INTERPOLATION_FUNCTION", tntDogControlParams.P_STEP_TARGET_INTERPOLATION_FUNCTION1);
//        dogControlParamsNameMap.put("GRF_REGULARIZER", tntDogControlParams.P_GRF_REGULARIZER);
//        dogControlParamsNameMap.put("GRF_TORQUE_TO_FORCE_OBJECTIVE_RATIO", tntDogControlParams.P_GRF_TORQUE_TO_FORCE_OBJECTIVE_RATIO);
//        dogControlParamsNameMap.put("MAX_CONTACT_POINTS_PER_FOOT", tntDogControlParams.P_MAX_CONTACT_POINTS_PER_FOOT);
//        dogControlParamsNameMap.put("STEP_FORWARD_OFFSET", tntDogControlParams.P_STEP_FORWARD_OFFSET);
//        dogControlParamsNameMap.put("GYRO_RATIO", tntDogControlParams.P_GYRO_RATIO);
//        dogControlParamsNameMap.put("STAND_STILL_THRESHOLD", tntDogControlParams.P_STAND_STILL_THRESHOLD);
//        dogControlParamsNameMap.put("EARLY_SWING_TERMINATE", tntDogControlParams.P_EARLY_SWING_TERMINATE);
//        dogControlParamsNameMap.put("LATE_SWING_TERMINATE", tntDogControlParams.P_LATE_SWING_TERMINATE);
//
//
//        return dogControlParamsNameMap;
//
//    }
//
//    private static Map<String, Integer> humanoidControlParamsNameMap = createDogControlParams();
//
//    private static Map<String, Integer> createHumanoidControlParams()
//    {
//        Map<String, Integer> humanoidControlParamsNameMap = new HashMap<>();
//
//        humanoidControlParamsNameMap.put("STRIDE_DURATION", tntHumanoidControlParams.P_STRIDE_DURATION);
//        humanoidControlParamsNameMap.put("DESIRED_HEIGHT", tntHumanoidControlParams.P_DESIRED_HEIGHT);
//        humanoidControlParamsNameMap.put("DESIRED_HEIGHT_TRAJ", tntHumanoidControlParams.P_DESIRED_HEIGHT_TRAJ1);
//        humanoidControlParamsNameMap.put("VFORCE_KP_SAGITTAL", tntHumanoidControlParams.P_VFORCE_KP_SAGITTAL);
//        humanoidControlParamsNameMap.put("VFORCE_KD_SAGITTAL", tntHumanoidControlParams.P_VFORCE_KD_SAGITTAL);
//        humanoidControlParamsNameMap.put("VFORCE_KP_CORONAL", tntHumanoidControlParams.P_VFORCE_KP_CORONAL);
//        humanoidControlParamsNameMap.put("VFORCE_KD_CORONAL", tntHumanoidControlParams.P_VFORCE_KD_CORONAL);
//        humanoidControlParamsNameMap.put("VFORCE_KP_VERTICAL", tntHumanoidControlParams.P_VFORCE_KP_VERTICAL);
//        humanoidControlParamsNameMap.put("VFORCE_KD_VERTICAL", tntHumanoidControlParams.P_VFORCE_KD_VERTICAL);
//        humanoidControlParamsNameMap.put("VFORCE_FF_VERTICAL_WEIGHT_PERCENTAGE", tntHumanoidControlParams.P_VFORCE_FF_VERTICAL_WEIGHT_PERCENTAGE);
//        humanoidControlParamsNameMap.put("BODYFRAME_TORQUE_KP", tntHumanoidControlParams.P_BODYFRAME_TORQUE_KP);
//        humanoidControlParamsNameMap.put("BODYFRAME_TORQUE_KD", tntHumanoidControlParams.P_BODYFRAME_TORQUE_KD);
//        humanoidControlParamsNameMap.put("BFRAME_LEAN_FORWARD", tntHumanoidControlParams.P_BFRAME_LEAN_FORWARD);
//        humanoidControlParamsNameMap.put("BFRAME_LEAN_SIDEWAYS", tntHumanoidControlParams.P_BFRAME_LEAN_SIDEWAYS);
//        humanoidControlParamsNameMap.put("BFRAME_LEAN_FORWARD_TRAJ", tntHumanoidControlParams.P_BFRAME_LEAN_FORWARD_TRAJ1);
//        humanoidControlParamsNameMap.put("BFRAME_LEAN_SIDEWAYS_TRAJ", tntHumanoidControlParams.P_BFRAME_LEAN_SIDEWAYS_TRAJ1);
//        humanoidControlParamsNameMap.put("SPINE_TWIST", tntHumanoidControlParams.P_SPINE_TWIST);
//        humanoidControlParamsNameMap.put("SPINE_SLOUCH_FORWARD", tntHumanoidControlParams.P_SPINE_SLOUCH_FORWARD);
//        humanoidControlParamsNameMap.put("SPINE_SLOUCH_SIDEWAYS", tntHumanoidControlParams.P_SPINE_SLOUCH_SIDEWAYS);
//        humanoidControlParamsNameMap.put("SPINE_TWIST_TRAJ", tntHumanoidControlParams.P_SPINE_TWIST_TRAJ1);
//        humanoidControlParamsNameMap.put("SPINE_SLOUCH_FORWARD_TRAJ", tntHumanoidControlParams.P_SPINE_SLOUCH_FORWARD_TRAJ1);
//        humanoidControlParamsNameMap.put("SPINE_SLOUCH_SIDEWAYS_TRAJ", tntHumanoidControlParams.P_SPINE_SLOUCH_SIDEWAYS_TRAJ1);
//        humanoidControlParamsNameMap.put("LEG_PLANE_ANGLE_LEFT", tntHumanoidControlParams.P_LEG_PLANE_ANGLE_LEFT);
//        humanoidControlParamsNameMap.put("LEG_PLANE_ANGLE_RIGHT", tntHumanoidControlParams.P_LEG_PLANE_ANGLE_RIGHT);
//        humanoidControlParamsNameMap.put("SWING_FOOT_HEIGHT_TRAJ", tntHumanoidControlParams.P_SWING_FOOT_HEIGHT_TRAJ1);
//        humanoidControlParamsNameMap.put("SWING_ANKLE_ROT", tntHumanoidControlParams.P_SWING_ANKLE_ROT1);
//        humanoidControlParamsNameMap.put("SWING_TOE_ROT", tntHumanoidControlParams.P_SWING_TOE_ROT1);
//        humanoidControlParamsNameMap.put("STANCE_ANKLE_ROT", tntHumanoidControlParams.P_STANCE_ANKLE_ROT1);
//        humanoidControlParamsNameMap.put("STANCE_TOE_ROT", tntHumanoidControlParams.P_STANCE_TOE_ROT1);
//        humanoidControlParamsNameMap.put("STEP_WIDTH", tntHumanoidControlParams.P_STEP_WIDTH);
//        humanoidControlParamsNameMap.put("STEP_FORWARD_OFFSET", tntHumanoidControlParams.P_STEP_FORWARD_OFFSET);
//        humanoidControlParamsNameMap.put("SWING_FOOT_VFORCE_KP", tntHumanoidControlParams.P_SWING_FOOT_VFORCE_KP);
//        humanoidControlParamsNameMap.put("SWING_FOOT_VFORCE_KD", tntHumanoidControlParams.P_SWING_FOOT_VFORCE_KD);
//        humanoidControlParamsNameMap.put("SWING_FOOT_MAX_FORCE", tntHumanoidControlParams.P_SWING_FOOT_MAX_FORCE);
//        humanoidControlParamsNameMap.put("LEFT_LEG_SWING_START", tntHumanoidControlParams.P_LEFT_LEG_SWING_START);
//        humanoidControlParamsNameMap.put("LEFT_LEG_SWING_END", tntHumanoidControlParams.P_LEFT_LEG_SWING_END);
//        humanoidControlParamsNameMap.put("RIGHT_LEG_SWING_START", tntHumanoidControlParams.P_RIGHT_LEG_SWING_START);
//        humanoidControlParamsNameMap.put("RIGHT_LEG_SWING_END", tntHumanoidControlParams.P_RIGHT_LEG_SWING_END);
//        humanoidControlParamsNameMap.put("STEP_TARGET_INTERPOLATION_FUNCTION", tntHumanoidControlParams.P_STEP_TARGET_INTERPOLATION_FUNCTION1);
//        humanoidControlParamsNameMap.put("GRF_REGULARIZER", tntHumanoidControlParams.P_GRF_REGULARIZER);
//        humanoidControlParamsNameMap.put("GRF_TORQUE_TO_FORCE_OBJECTIVE_RATIO", tntHumanoidControlParams.P_GRF_TORQUE_TO_FORCE_OBJECTIVE_RATIO);
//        humanoidControlParamsNameMap.put("MAX_CONTACT_POINTS_PER_FOOT", tntHumanoidControlParams.P_MAX_CONTACT_POINTS_PER_FOOT);
//        humanoidControlParamsNameMap.put("FOOT_VELOCITY_MATCH", tntHumanoidControlParams.P_FOOT_VELOCITY_MATCH);
//        humanoidControlParamsNameMap.put("GYRO_RATIO", tntHumanoidControlParams.P_GYRO_RATIO);
//        humanoidControlParamsNameMap.put("STAND_STILL_THRESHOLD", tntHumanoidControlParams.P_STAND_STILL_THRESHOLD);
//        humanoidControlParamsNameMap.put("EARLY_SWING_TERMINATE", tntHumanoidControlParams.P_EARLY_SWING_TERMINATE);
//        humanoidControlParamsNameMap.put("LATE_SWING_TERMINATE", tntHumanoidControlParams.P_LATE_SWING_TERMINATE);
//
//
//        return humanoidControlParamsNameMap;
//
//    }
//
//
//    private static Dictionary<int, String> fragmentHostControllerMap = new Dictionary<int, String>();

    public static boolean DeserializeFromJsonStr(GVRContext context, String filepath)
    {

        //get the json string
        String jsonstr = null;
        try {
            InputStream is = context.getActivity().getAssets().open(filepath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonstr = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(jsonstr == null)
        {
            Log.e("AVTDeserialiser", "avt filepath not found");
            return false;
        }


        //get json object mapper from string
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(jsonstr)  ;
        }catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        if(root == null)
            return false;

        JsonNode multiBodyToken = root.get("Multi Body");
        GVRSceneObject characterGO = new GVRSceneObject(context);
        characterGO.setName(multiBodyToken.path("value").path("Name").asText("rootObject"));

        //todo: not implemented this yet? not sure if necessary
//        chracterGO.AddComponent<DeepMotionAvatar>();

        JsonNode multiBody = multiBodyToken.path("value");

        //todo
//        tntBase theBase = CreateMultiBody(multiBody, characterGO);
//
//        if (theBase != null)
//        {
//            //todo
//            theBase.AssignValidChildLinkIndices(true);
//
//
//            //todo
//            JsonNode controllers = root.path("Controllers");
//            CreatControllers(theBase, controllers);
//
//
//            //todo:
//            // render mesh (skinned mesh)
//            chracterGO.AddComponent<createMesh>().setBoneMapping(simulationRenderBoneMap.Keys.ToList(),
//                simulationRenderBoneMap.Values.ToList());



//        GVRSceneObject theBase = CreateMultiBody(multiBody, characterGO);




        return true;
        }


//    private static GVRSceneObject CreateMultiBody(JsonNode multiBodyToken, GVRSceneObject go, GVRContext context)
//    {
//
//        // base
//        JsonNode baseToken = multiBodyToken.path("value").path("Base Bone");
//        String baseName = baseToken.path("Name").asText();
//        GVRSceneObject root = new GVRSceneObject(context);
//        root.setName(baseName);
//
//
//        tntBase theBase = CreateBaseLink(baseToken, root);
//        theBase.m_highDefIntegrator = GetTokenValue<bool>(multiBodyToken["High Def Integrator"]);
//        if (multiBodyToken["Is Kinematic"] != null) theBase.m_IsKinematic = GetTokenValue<bool>(multiBodyToken["Is Kinematic"]);
//        theBase.m_linearDamping = GetTokenValue<float>(multiBodyToken["Linear Damping"]);
//        theBase.m_maxAppliedImpulse = GetTokenValue<float>(multiBodyToken["Max Applied Impulse"]);
//        theBase.m_maxCoordinateVelocity = GetTokenValue<float>(multiBodyToken["Max Coord Vel"]);
//        theBase.EnableSelfCollision = GetTokenValue<bool>(multiBodyToken["Self Collision"]);
//        theBase.m_simulationFrequencyMultiplier = GetTokenValue<int>(multiBodyToken["Simulation Freq. Mult"]);
//        theBase.RequiredSolverFidelityIndex = GetTokenValue<int>(multiBodyToken["Solver Fidelity Index"]);
//        theBase.m_useGlobalVel = GetTokenValue<bool>(multiBodyToken["Use Global Vel"]);
//        theBase.UseWorldSolverFidelityIndex = GetTokenValue<bool>(multiBodyToken["Use World Solver Fidelity Index"]);
//        linkNameMap.Add(baseName, theBase);
//        simulationRenderBoneMap.Add(baseName, GetTokenValue<String>(baseToken["Target Bone"]));
//        root.transform.parent = go.transform;
//
//        // child links
//        bool childLinksUseGlobalTransform = true;// GetTokenValue<bool>(multiBodyToken["Child Bones Use Global Transform"]);
//        JToken childLinksToken = GetTokenVersioned(multiBodyToken["Child Bones"]);
//        foreach (JToken childLinkToken in childLinksToken)
//        {
//            String childLinkName = GetTokenValue<String>(childLinkToken["value"]["Name"]);
//            GameObject link = new GameObject(childLinkName);
//            tntChildLink childLink = CreateChildLink(childLinkToken["value"], link, childLinksUseGlobalTransform);
//            linkNameMap.Add(childLinkName, childLink);
//            simulationRenderBoneMap.Add(childLinkName, GetTokenValue<String>(childLinkToken["Target Bone"]));
//            link.transform.parent = go.transform;
//        }
//
//        return theBase;
//    }



//    private static void CreatControllers(tntBase theBase, JToken controllersToken)
//    {
//        foreach (JToken controllerToken in controllersToken)
//        {
//            CreateController(theBase, controllerToken);
//        }
//    }
//
//    public static void CreateController(tntBase theBase, JToken controllerToken)
//    {
//        String characterName = GetTokenValue<String>(controllerToken["value"]["character"]);
//        if (characterName != theBase.transform.parent.name)
//            return;
//
//        String controllerType = GetTokenValue<String>(controllerToken["type"]);
//        GameObject controllerGO = new GameObject(GetTokenValue<String>(controllerToken["value"]["Name"]));
//        controllerGO.transform.parent = theBase.gameObject.transform;
//
//        if (controllerType == "dmDogController")
//        {
//            tntDogController dogController = controllerGO.AddComponent<tntDogController>();
//            FillController(dogController, controllerToken["value"], theBase);
//        }
//        else if (controllerType == "dmHumanoidController")
//        {
//            tntHumanoidController humanoidController = controllerGO.AddComponent<tntHumanoidController>();
//            FillController(humanoidController, controllerToken["value"], theBase);
//        }
//        else if (controllerType == "dmControlGraphController")
//        {
//            fragmentHostControllerMap.Clear();
//            ControlGraphController controlGraphController = controllerGO.AddComponent<ControlGraphController>();
//            FillController(controlGraphController, controllerToken["value"], theBase, controllerGO);
//            // find the game object holds TrackingControlFragmentHost
//            var controlFragHost = controllerGO.GetComponentInChildren<TrackingControlFragmentHost>();
//            // fine the tntClipGuidedPoseController game object since it is presumebaly always in front of dmControlGraphController
//            var clipGuidedPoseController = theBase.gameObject.GetComponentInChildren<tntClipGuidedPoseController>();
//            // reparent
//            if (fragmentHostControllerMap[controlFragHost.GetInstanceID()] == clipGuidedPoseController.gameObject.name)
//                clipGuidedPoseController.gameObject.transform.parent = controlFragHost.gameObject.transform;
//        }
//        else if (controllerType == "dmTrackingController")
//        {
//            tntClipGuidedPoseController clipGuidedPoseController = controllerGO.AddComponent<tntClipGuidedPoseController>();
//            FillController(clipGuidedPoseController, controllerToken["value"], theBase);
//        }
//
//        //link PD params
//        if (controllerToken["value"]["linkPdParams"] != null)
//        {
//            foreach (JToken linkPdParamToken in GetTokenVersioned(controllerToken["value"]["linkPdParams"]))
//            {
//                tntChildLink link = FindLinkByName(GetTokenValue<String>(linkPdParamToken["Joint Name"])) as tntChildLink;
//                if (link)
//                {
//                    link.m_kd = GetTokenValue<float>(linkPdParamToken["KD"]);
//                    link.m_kp = GetTokenValue<float>(linkPdParamToken["KP"]);
//                    link.m_maxPDTorque = GetTokenValue<float>(linkPdParamToken["Max PD Torque"]);
//                    link.m_useSoftConstraint = GetTokenValue<bool>(linkPdParamToken["Use Soft Constraint"]);
//                }
//            }
//        }
//    }
//
//    // TODO: code duplication!
//    // as soon as a common base class of tntHumanoidController and tntDogController is create this code duplication needs to be eliminated
//    public static void FillController(tntHumanoidController controller, JToken controllerToken, tntBase theBase)
//    {
//        controller.m_blendGranularity = GetTokenValue<float>(controllerToken["blendGranularity"]);
//        if (controllerToken["disableGRFSolver"] != null)
//        {
//            bool disableGRFSolver = GetTokenValue<bool>(controllerToken["disableGRFSolver"]);
//            controller.m_GRFSolverMode = disableGRFSolver ? PhysicsAPI.TNT.ControllerGRFSolverMode.Basic : PhysicsAPI.TNT.ControllerGRFSolverMode.Standard;
//            Debug.LogWarning("disableGRFSolver is deprecated, please use GRFSolverMode instead!");
//        }
//        controller.m_GRFSolverMode = GetTokenValue<PhysicsAPI.TNT.ControllerGRFSolverMode>(controllerToken["GRFSolverMode"]);
//        controller.m_constraintPDFix = GetTokenValue<bool>(controllerToken["enableConstraintPDFix"]);
//        controller.m_keepRootPosition = GetTokenValue<bool>(controllerToken["keepRootPosition"]);
//        controller.m_useBlendSpace = GetTokenValue<bool>(controllerToken["useBlendspace"]);
//        controller.m_forward = GetVector3(controllerToken["forward"], Vector3.forward);
//        controller.m_right = GetVector3(controllerToken["right"], Vector3.right);
//
//        //current pose
//        controller.m_currentPose = new tntReducedState();
//        controller.m_currentPose.NumOfChildLinks = linkNameMap.Count - 1;
//        controller.m_currentPose.AllocArrays();
//        FillReducedState(controller.m_currentPose, GetTokenVersioned(controllerToken["currentPose"]));
//
//        //desired pose
//        controller.m_desiredPose = new tntReducedState();
//        controller.m_desiredPose.NumOfChildLinks = linkNameMap.Count - 1;
//        controller.m_desiredPose.AllocArrays();
//        FillReducedState(controller.m_desiredPose, GetTokenVersioned(controllerToken["desiredPose"]));
//
//        // limbs
//        controller.m_limbs = new LimbConfiguration();
//        FillLimbConfiguration(controller.m_limbs, GetTokenVersioned(controllerToken["limbs"]), theBase);
//
//        //param set
//        controller.m_controlParams = new tntHumanoidControlParams(); ;
//        FillControlParamSet(controller.m_controlParams.m_params, GetTokenVersioned(controllerToken["paramSet"]), humanoidControlParamsNameMap);
//
//        //root PD params
//        controller.m_rootPdParams = new PDParams();
//        FillPDParams(controller.m_rootPdParams, controllerToken["rootPdParams"]);
//    }
//
//    public static void FillController(tntDogController controller, JToken controllerToken, tntBase theBase)
//    {
//        controller.m_blendGranularity = GetTokenValue<float>(controllerToken["blendGranularity"]);
//        if (controllerToken["disableGRFSolver"] != null)
//        {
//            bool disableGRFSolver = GetTokenValue<bool>(controllerToken["disableGRFSolver"]);
//            controller.m_GRFSolverMode = disableGRFSolver ? PhysicsAPI.TNT.ControllerGRFSolverMode.Basic : PhysicsAPI.TNT.ControllerGRFSolverMode.Standard;
//            Debug.LogWarning("disableGRFSolver is deprecated, please use GRFSolverMode instead!");
//        }
//        controller.m_GRFSolverMode = GetTokenValue<PhysicsAPI.TNT.ControllerGRFSolverMode>(controllerToken["GRFSolverMode"]);
//        controller.m_constraintPDFix = GetTokenValue<bool>(controllerToken["enableConstraintPDFix"]);
//        controller.m_keepRootPosition = GetTokenValue<bool>(controllerToken["keepRootPosition"]);
//        controller.m_useBlendSpace = GetTokenValue<bool>(controllerToken["useBlendspace"]);
//        controller.m_forward = GetVector3(controllerToken["forward"], Vector3.forward);
//        controller.m_right = GetVector3(controllerToken["right"], Vector3.right);
//
//        //current pose
//        controller.m_currentPose = new tntReducedState();
//        controller.m_currentPose.NumOfChildLinks = linkNameMap.Count - 1;
//        controller.m_currentPose.AllocArrays();
//        FillReducedState(controller.m_currentPose, controllerToken["currentPose"]);
//
//        //desired pose
//        controller.m_desiredPose = new tntReducedState();
//        controller.m_desiredPose.NumOfChildLinks = linkNameMap.Count - 1;
//        controller.m_desiredPose.AllocArrays();
//        FillReducedState(controller.m_desiredPose, controllerToken["desiredPose"]);
//
//        // limbs
//        controller.m_limbs = new LimbConfiguration();
//        FillLimbConfiguration(controller.m_limbs, controllerToken["limbs"], theBase);
//
//        //param set
//        controller.m_controlParams = new tntDogControlParams();
//        FillControlParamSet(controller.m_controlParams.m_params, controllerToken["paramSet"], dogControlParamsNameMap);
//
//        //root PD params
//        controller.m_rootPdParams = new PDParams();
//        FillPDParams(controller.m_rootPdParams, controllerToken["rootPdParams"]);
//    }
//
//    public static void FillController(ControlGraphController controller, JToken controllerToken, tntBase theBase, GameObject controllerGO)
//    {
//        controller.m_constraintPDFix = GetTokenValue<bool>(controllerToken["enableConstraintPDFix"]);
//        controller.NeedToMirrorResetState = GetTokenValue<bool>(controllerToken["needToMirrorResetState"]);
//        //var startState = ScriptableObject.CreateInstance<tntReducedState>();
//        //startState.NumOfChildLinks = linkNameMap.Count - 1;
//        //startState.AllocArrays();
//        //FillReducedState(startState, controllerToken["resetState"]);
//        //controller.StartState = startState;
//        controller.Root = theBase;
//
//        ControlGraph controlGraph = controllerGO.AddComponent<ControlGraph>();
//        FillControlGraph(controlGraph, controllerToken["controlGraph"], controllerGO);
//
//        GraphControlFragmentScheduler scheduler = controllerGO.AddComponent<GraphControlFragmentScheduler>();
//    }
//
//    public static void FillController(tntClipGuidedPoseController controller, JToken controllerToken, tntBase theBase)
//    {
//        controller.m_constraintPDFix = GetTokenValue<bool>(controllerToken["enableConstraintPDFix"]);
//        controller.PerLinkKdScaler = GetTokenValue<float>(controllerToken["globalKdScale"]);
//        controller.PerLinkKpScaler = GetTokenValue<float>(controllerToken["globalKpScale"]);
//        controller.PerLinkMaxTorqueScaler = GetTokenValue<float>(controllerToken["globalMaxTorqueScale"]);
//        controller.TrackingPDControlMode =
//                GetPDControlModeFromString(GetTokenValue<String>(controllerToken["globalPDControlMode"]));
//    }
//
//    public static void FillReducedState(tntReducedState state, JToken stateToken)
//    {
//        if (!stateToken.HasValues)
//            return;
//
//        state.m_values[3] = GetTokenValue<float>(stateToken["heading"]);
//
//        Vector3 v = GetVector3(stateToken["headingAxes"], Vector3.up);
//        state.m_values[0] = v.x;
//        state.m_values[1] = v.y;
//        state.m_values[2] = v.z;
//
//        v = GetVector3(stateToken["rootAngularVelocity"]);
//        state.m_values[14] = v.x;
//        state.m_values[15] = v.y;
//        state.m_values[16] = v.z;
//
//        Quaternion q = GetQuaternion(stateToken["rootOrientation"]);
//        state.m_values[7] = q.x;
//        state.m_values[8] = q.y;
//        state.m_values[9] = q.z;
//        state.m_values[10] = q.w;
//
//        v = GetVector3(stateToken["rootPosition"]);
//        state.m_values[4] = v.x;
//        state.m_values[5] = v.y;
//        state.m_values[6] = v.z;
//
//        v = GetVector3(stateToken["rootVelocity"]);
//        state.m_values[11] = v.x;
//        state.m_values[12] = v.y;
//        state.m_values[13] = v.z;
//
//        JToken linkStatusValuesToken = GetTokenVersioned(stateToken["linksStatus"]);
//        float[] values = new float[linkStatusValuesToken.Count()];
//        int valueIndex = 0;
//        foreach (JToken linkStatusToken in linkStatusValuesToken)
//        {
//            values[valueIndex++] = GetTokenValue<float>(linkStatusToken);
//        }
//
//        JToken linkNamesToken = GetTokenVersioned(stateToken["linkNames"]);
//        if (linkNamesToken != null)
//        {
//            foreach (JToken linkNameToken in linkNamesToken)
//            {
//                String linkName = GetTokenValue<String>(linkNameToken);
//                // find the link using the name, if found set the value in state.m_values accordingly
//                tntLink link = FindLinkByName(linkName);
//                if (link)
//                {
//                    int linkIndex = link.GetIndex();
//                    Quaternion linkOrientation;
//                    linkOrientation.x = values[linkIndex * 7];
//                    linkOrientation.y = values[linkIndex * 7 + 1];
//                    linkOrientation.z = values[linkIndex * 7 + 2];
//                    linkOrientation.w = values[linkIndex * 7 + 3];
//                    state.SetJointOrientationToQuaternion(linkIndex, linkOrientation);
//
//                    Vector3 linkAngularVelocity;
//                    linkAngularVelocity.x = values[linkIndex * 7 + 4];
//                    linkAngularVelocity.y = values[linkIndex * 7 + 5];
//                    linkAngularVelocity.z = values[linkIndex * 7 + 6];
//                    state.SetJointAngularVelocity(linkIndex, linkAngularVelocity);
//                }
//            }
//        }
//    }
//
//    public static void FillLimbConfiguration(LimbConfiguration limbConfig, JToken limbConfigToken, tntBase theBase)
//    {
//        limbConfig.m_antiLegCrossing = GetTokenValue<bool>(limbConfigToken["antiLegCrossing"]);
//        limbConfig.m_deadThresholdGRF = GetTokenValue<float>(limbConfigToken["deadThresholdGRF"]);
//        limbConfig.m_deadThresholdSwingLeg = GetTokenValue<float>(limbConfigToken["deadThresholdSwingLeg"]);
//        // TODO: Fix the name when the reflection layer is updated
//        limbConfig.m_naturalHeading = GetTokenValue<bool>(limbConfigToken["enableLean"]);
//        limbConfig.m_kickIfDead = GetTokenValue<bool>(limbConfigToken["kickIfDead"]);
//        limbConfig.m_limbMaxTrackingForce = GetTokenValue<float>(limbConfigToken["limbMaxTrackingForce"]);
//        limbConfig.m_limbTrackingKd = GetTokenValue<float>(limbConfigToken["limbTrackingKd"]);
//        limbConfig.m_limbTrackingKp = GetTokenValue<float>(limbConfigToken["limbTrackingKp"]);
//        limbConfig.m_stepRelativeToCOM = GetTokenValue<bool>(limbConfigToken["stepRelativeToCOM"]);
//        limbConfig.m_stepRelativeToRoot = GetTokenValue<bool>(limbConfigToken["stepRelativeToRoot"]);
//
//        limbConfig.m_lHand = FindLinkByName(GetTokenValue<String>(limbConfigToken["lHand"]));
//        limbConfig.m_rHand = FindLinkByName(GetTokenValue<String>(limbConfigToken["rHand"]));
//        limbConfig.m_lShoulder = FindLinkByName(GetTokenValue<String>(limbConfigToken["lShoulder"]));
//        limbConfig.m_rShoulder = FindLinkByName(GetTokenValue<String>(limbConfigToken["rShoulder"]));
//        limbConfig.m_lToes = FindLinkByName(GetTokenValue<String>(limbConfigToken["lToes"]));
//        limbConfig.m_rToes = FindLinkByName(GetTokenValue<String>(limbConfigToken["rToes"]));
//        limbConfig.m_neck = FindLinkByName(GetTokenValue<String>(limbConfigToken["neck"]));
//        limbConfig.m_upperBack = FindLinkByName(GetTokenValue<String>(limbConfigToken["upperBack"]));
//        limbConfig.m_lowerBack = FindLinkByName(GetTokenValue<String>(limbConfigToken["lowerBack"]));
//        limbConfig.m_tail = FindLinkByName(GetTokenValue<String>(limbConfigToken["tail"]));
//    }
//
//    public static tntLink FindLinkByName(String name)
//    {
//        tntLink result;
//        if (linkNameMap.TryGetValue(name, out result))
//        {
//            return result;
//        }
//        else
//        {
//            return null;
//        }
//    }
//
//    public static void FillControlParamSet(float[] paramSet, JToken paramSetToken, Dictionary<String, int> controlParamNameMap)
//    {
//        foreach (JProperty paramProperty in paramSetToken.Children<JProperty>())
//        {
//            int index;
//            if (controlParamNameMap.TryGetValue(paramProperty.Name, out index))
//            {
//                if (GetTokenVersioned(paramProperty.Value).Type == JTokenType.Array)
//                {
//                    foreach (JToken v in GetTokenVersioned(paramProperty.Value))
//                    {
//                        paramSet[index++] = GetTokenValue<float>(v);
//                    }
//                }
//                else
//                {
//                    paramSet[index] = GetTokenValue<float>(GetTokenVersioned(paramProperty.Value));
//                }
//            }
//        }
//    }
//
//    public static void FillPDParams(PDParams param, JToken PDParamssToken)
//    {
//        param.controlled = GetTokenValue<bool>(PDParamssToken["Controlled"]);
//        param.kd = GetTokenValue<float>(PDParamssToken["KD"]);
//        param.kp = GetTokenValue<float>(PDParamssToken["KP"]);
//        param.maxAbsTorque = GetTokenValue<float>(PDParamssToken["Max PD Torque"]);
//        param.relToCharFrame = GetTokenValue<bool>(PDParamssToken["Rel To Char Frame"]);
//    }
//
//    public static void FillControlGraph(ControlGraph controlGraph, JToken controlGraphToken, GameObject controllerGO)
//    {
//        JToken fragmentHostsArray = controlGraphToken["fragmentHosts"];
//        GameObject trackingHostGO = new GameObject(GetTokenValue<String>(fragmentHostsArray[0]["value"]["Name"]));
//        trackingHostGO.transform.parent = controllerGO.gameObject.transform;
//        TrackingControlFragmentHost fragmentHost = trackingHostGO.AddComponent<TrackingControlFragmentHost>();
//        FillTrackingControlFragementHost(fragmentHost, fragmentHostsArray[0]["value"]);
//    }
//
//    public static void FillTrackingControlFragementHost(TrackingControlFragmentHost fragmentHost, JToken fragmentHostToken)
//    {
//        fragmentHostControllerMap.Add(fragmentHost.GetInstanceID(), GetTokenValue<String>(fragmentHostToken["controller"]));
//        var encodedBuffer = GetTokenValue<String>(fragmentHostToken["data"]).ToCharArray();
//        fragmentHost.HostContent = ScriptableObject.CreateInstance<TrackingControlFragmentHostContent>();
//        fragmentHost.HostContent.RawBuffer =
//                System.Convert.FromBase64CharArray(encodedBuffer, 0, encodedBuffer.Length);
//        fragmentHost.HostID = GetTokenValue<String>(fragmentHostToken["hostID"]);
//    }
//
//    private static tntBase CreateMultiBody(JToken multiBodyToken, GameObject go)
//    {
//        linkNameMap.Clear();
//        simulationRenderBoneMap.Clear();
//
//        // base
//        JToken baseToken = GetTokenVersioned(multiBodyToken["Base Bone"]);
//        String baseName = GetTokenValue<String>(baseToken["Name"]);
//        GameObject root = new GameObject(baseName);
//        tntBase theBase = CreateBaseLink(baseToken, root);
//        theBase.m_highDefIntegrator = GetTokenValue<bool>(multiBodyToken["High Def Integrator"]);
//        if (multiBodyToken["Is Kinematic"] != null) theBase.m_IsKinematic = GetTokenValue<bool>(multiBodyToken["Is Kinematic"]);
//        theBase.m_linearDamping = GetTokenValue<float>(multiBodyToken["Linear Damping"]);
//        theBase.m_maxAppliedImpulse = GetTokenValue<float>(multiBodyToken["Max Applied Impulse"]);
//        theBase.m_maxCoordinateVelocity = GetTokenValue<float>(multiBodyToken["Max Coord Vel"]);
//        theBase.EnableSelfCollision = GetTokenValue<bool>(multiBodyToken["Self Collision"]);
//        theBase.m_simulationFrequencyMultiplier = GetTokenValue<int>(multiBodyToken["Simulation Freq. Mult"]);
//        theBase.RequiredSolverFidelityIndex = GetTokenValue<int>(multiBodyToken["Solver Fidelity Index"]);
//        theBase.m_useGlobalVel = GetTokenValue<bool>(multiBodyToken["Use Global Vel"]);
//        theBase.UseWorldSolverFidelityIndex = GetTokenValue<bool>(multiBodyToken["Use World Solver Fidelity Index"]);
//        linkNameMap.Add(baseName, theBase);
//        simulationRenderBoneMap.Add(baseName, GetTokenValue<String>(baseToken["Target Bone"]));
//        root.transform.parent = go.transform;
//
//        // child links
//        bool childLinksUseGlobalTransform = true;// GetTokenValue<bool>(multiBodyToken["Child Bones Use Global Transform"]);
//        JToken childLinksToken = GetTokenVersioned(multiBodyToken["Child Bones"]);
//        foreach (JToken childLinkToken in childLinksToken)
//        {
//            String childLinkName = GetTokenValue<String>(childLinkToken["value"]["Name"]);
//            GameObject link = new GameObject(childLinkName);
//            tntChildLink childLink = CreateChildLink(childLinkToken["value"], link, childLinksUseGlobalTransform);
//            linkNameMap.Add(childLinkName, childLink);
//            simulationRenderBoneMap.Add(childLinkName, GetTokenValue<String>(childLinkToken["Target Bone"]));
//            link.transform.parent = go.transform;
//        }
//
//        return theBase;
//    }
//
//    private static tntBase CreateBaseLink(JToken baseToken, GameObject go)
//    {
//        tntBase baseComponent = go.AddComponent<tntBase>();
//        if (baseToken["Collision Layer ID"] != null)
//            baseComponent.SetLayerID(GetTokenValue<int>(baseToken["Collision Layer ID"]));
//
//        FillLink(baseComponent as tntLink, baseToken, false);
//
//        CreateColliders(GetTokenVersioned(baseToken["Collider"]), go);
//
//        return baseComponent;
//    }
//
//    public static tntChildLink CreateChildLink(JToken childLinkToken, GameObject go, bool useGlobalTrans)
//    {
//        String jointType = GetTokenValue<String>(childLinkToken["Joint Type"]);
//
//        if (jointType == "ball")
//        {
//            tntBallLink ballLink = go.AddComponent<tntBallLink>();
//            FillChildLink(ballLink, childLinkToken, useGlobalTrans);
//            return ballLink;
//        }
//        else if (jointType == "hinge")
//        {
//            tntHingeLink hingeLink = go.AddComponent<tntHingeLink>();
//            FillChildLink(hingeLink, childLinkToken, useGlobalTrans);
//            return hingeLink;
//        }
//        else if (jointType == "universal")
//        {
//            tntUniversalLink universalLink = go.AddComponent<tntUniversalLink>();
//            FillChildLink(universalLink, childLinkToken, useGlobalTrans);
//            return universalLink;
//        }
//
//        return null;
//    }
//
//    public static void FillChildLink(tntChildLink childLink, JToken childLinkToken, bool useGlobalTrans)
//    {
//        if (childLink is tntHingeLink)
//        {
//            (childLink as tntHingeLink).m_axisA = GetVector3(childLinkToken["Axis A"]);
//        }
//        else if (childLink is tntUniversalLink)
//        {
//            tntUniversalLink universalLink = childLink as tntUniversalLink;
//            universalLink.m_axisA = GetVector3(childLinkToken["Axis A"]);
//            universalLink.m_axisB = GetVector3(childLinkToken["Axis B"]);
//        }
//
//        childLink.m_breakingReactionImpulse = GetTokenValue<float>(childLinkToken["Breaking Rection Impulse"]);
//        childLink.CollideWithParent = GetTokenValue<bool>(childLinkToken["Collide With Parent"]);
//        childLink.SetLayerID(GetTokenValue<int>(childLinkToken["Collision Layer ID"]));
//        String parentName = GetTokenValue<String>(childLinkToken["Parent"]);
//        // TODO: if we can't guaratee to always have parent link befor the child link we can't do the following line
//        childLink.m_parent = FindLinkByName(parentName);
//        childLink.PivotA = GetVector3(childLinkToken["Pivot A"]);
//        childLink.PivotB = GetVector3(childLinkToken["Pivot B"]);
//
//        childLink.m_kp = GetTokenValue<float>(GetTokenVersioned(childLinkToken["Joint PD Param"])["KP"]);
//        childLink.m_kd = GetTokenValue<float>(GetTokenVersioned(childLinkToken["Joint PD Param"])["KD"]);
//        childLink.m_maxPDTorque = GetTokenValue<float>(GetTokenVersioned(childLinkToken["Joint PD Param"])["Max PD Torque"]);
//        childLink.m_useSoftConstraint = GetTokenValue<bool>(GetTokenVersioned(childLinkToken["Joint PD Param"])["Use Soft Constraint"]);
//
//        JToken dofDataArray = GetTokenVersioned(childLinkToken["DOF Data"]);
//        int dofIndex = 0;
//        foreach (JToken dofDataToken in dofDataArray)
//        {
//            FillDofData(childLink.m_dofData[dofIndex++], dofDataToken);
//        }
//
//        FillLink(childLink as tntLink, childLinkToken, useGlobalTrans);
//
//        CreateColliders(GetTokenVersioned(childLinkToken["Collider"]), childLink.gameObject);
//    }
//
//    public static void FillDofData(tntDofData dofData, JToken dofDataToken)
//    {
//        dofData.m_desiredPosition = GetTokenValue<float>(dofDataToken["desiredPosition"]);
//        dofData.m_desiredVelocity = GetTokenValue<float>(dofDataToken["desiredVelocity"]);
//        dofData.m_isPositionMotor = GetTokenValue<bool>(dofDataToken["isPositionMotor"]);
//        dofData.m_limitHigh = GetTokenValue<float>(dofDataToken["limitHigh"]);
//        dofData.m_limitLow = GetTokenValue<float>(dofDataToken["limitLow"]);
//        dofData.m_maxLimitForce = GetTokenValue<float>(dofDataToken["maxLimitForce"]);
//        dofData.m_maxMotorForce = GetTokenValue<float>(dofDataToken["maxMotorForce"]);
//        dofData.m_neutralPoint = GetTokenValue<float>(dofDataToken["neutralPoint"]);
//        dofData.m_positionLockThreshold = GetTokenValue<float>(dofDataToken["positionLockThreshold"]);
//        dofData.m_springDamping = GetTokenValue<float>(dofDataToken["springDamping"]);
//        dofData.m_springStiffness = GetTokenValue<float>(dofDataToken["springStiffness"]);
//        dofData.m_useAutomaticPositionLockThreshold = GetTokenValue<bool>(dofDataToken["useAutomaticPositionLockThreshold"]);
//        dofData.m_useLimit = GetTokenValue<bool>(dofDataToken["useLimit"]);
//        dofData.m_useMotor = GetTokenValue<bool>(dofDataToken["useMotor"]);
//    }
//
//    public static void FillLink(tntLink link, JToken linkToken, bool useGlobalTrans)
//    {
//        link.m_collidable = GetTokenValue<bool>(linkToken["Collidable"]);
//        FillEntity(link as tntEntity, linkToken, useGlobalTrans);
//    }
//
//    public static void FillEntity(tntEntity entity, JToken entityToken, bool useGlobalTrans)
//    {
//        GetTransformation(entityToken["Transform"], entity.gameObject, useGlobalTrans);
//        entity.m_moi = GetVector3(entityToken["Moments of Inertia"]);
//        entity.mass = GetTokenValue<float>(entityToken["Mass"]);
//        entity.m_material = new PhysicMaterial();
//        entity.m_material.staticFriction = entity.m_material.dynamicFriction = GetTokenValue<float>(GetTokenVersioned(entityToken["Physic Material"])["Friction"]);
//        entity.m_material.bounciness = GetTokenValue<float>(GetTokenVersioned(entityToken["Physic Material"])["Restitution"]);
//        entity.m_material.frictionCombine = GetPhysicMaterialCombineFromString(GetTokenValue<String>(GetTokenVersioned(entityToken["Physic Material"])["Friction Combine Mode"]));
//        entity.m_material.bounceCombine = GetPhysicMaterialCombineFromString(GetTokenValue<String>(GetTokenVersioned(entityToken["Physic Material"])["Restitution Combine Mode"]));
//    }
//
//    public static PhysicMaterialCombine GetPhysicMaterialCombineFromString(String mode)
//    {
//        try
//        {
//            return (PhysicMaterialCombine)Enum.Parse(typeof(PhysicMaterialCombine), mode);
//        }
//        catch (ArgumentException)
//        {
//            Console.WriteLine("'{0}' is not a member of the PhysicMaterialCombine enumeration.", mode);
//            return PhysicMaterialCombine.Multiply;
//        }
//    }
//
//    public static tntClipGuidedPoseController.PDControlMode GetPDControlModeFromString(String mode)
//    {
//        try
//        {
//            return (tntClipGuidedPoseController.PDControlMode)Enum.Parse(typeof(tntClipGuidedPoseController.PDControlMode), mode);
//        }
//        catch (ArgumentException)
//        {
//            Console.WriteLine("'{0}' is not a member of the tntClipGuidedPoseController.PDControlMode enumeration.", mode);
//            return tntClipGuidedPoseController.PDControlMode.ExplicitWithConstraintBasedDamping;
//        }
//    }
//
//    public static void CreateColliders(JToken collidersToken, GameObject go)
//    {
//        if (collidersToken["type"] == null)
//            return;
//
//        String colliderType = GetTokenValue<String>(collidersToken["type"]);
//        if (colliderType == "dmCompoundCollider")
//        {
//            go.AddComponent<tntCompoundCollider>();
//            JToken childColliders = GetTokenVersioned(collidersToken["value"]["Child Colliders"]);
//            foreach (JToken childCollider in childColliders)
//            {
//                GameObject collider = new GameObject(GetTokenValue<String>(collidersToken["value"]["Name"]));
//                collider.transform.parent = go.transform;
//                tntRigidBody rb = collider.AddComponent<tntRigidBody>();
//                rb.mass = go.GetComponent<tntEntity>().mass / childColliders.Count();
//                CreateColliders(childCollider, collider);
//            }
//        }
//        else if (colliderType == "dmBoxCollider")
//        {
//            BoxCollider box = go.AddComponent<BoxCollider>();
//            box.size = 2.0f * GetVector3(collidersToken["value"]["Half Size"], Vector3.one);
//            GetTransformation(collidersToken["value"]["Transform"], go, false);
//        }
//        else if (colliderType == "dmSphereCollider")
//        {
//            SphereCollider sphere = go.AddComponent<SphereCollider>();
//            sphere.radius = GetTokenValue<float>(collidersToken["value"]["Radius"]);
//            GetTransformation(collidersToken["value"]["Transform"], go, false);
//        }
//        else if (colliderType == "dmCapsuleCollider")
//        {
//            CapsuleCollider capsule = go.AddComponent<CapsuleCollider>();
//            String direction = GetTokenValue<String>(collidersToken["value"]["Direction"]);
//            capsule.direction = direction.ToCharArray()[0] - 'X';
//            capsule.height = GetTokenValue<float>(collidersToken["value"]["Height"]);
//            capsule.radius = GetTokenValue<float>(collidersToken["value"]["Radius"]);
//            GetTransformation(collidersToken["value"]["Transform"], go, false);
//            capsule.height = (capsule.height + capsule.radius) * 2.0f; // kernel's capsule collider height has different definition from Unity
//            switch (capsule.direction)
//            {
//                case 0:
//                    capsule.height /= go.transform.localScale.x;
//                    capsule.radius /= go.transform.localScale.y;
//                    break;
//                case 1:
//                    capsule.height /= go.transform.localScale.y;
//                    capsule.radius /= go.transform.localScale.x;
//                    break;
//                case 2:
//                    capsule.height /= go.transform.localScale.z;
//                    capsule.radius /= go.transform.localScale.x;
//                    break;
//            }
//        }
//    }
//
//    public static Vector3 GetVector3(JToken vecToken, Vector3 defaultValue = default(Vector3))
//    {
//        if (vecToken == null)
//            return defaultValue;
//
//        Vector3 result;
//
//        if (GetTokenVersioned(vecToken).Type != JTokenType.Array)
//        {
//            result.x = GetTokenValue<float>(GetTokenVersioned(vecToken)["X"]);
//            result.y = GetTokenValue<float>(GetTokenVersioned(vecToken)["Y"]);
//            result.z = GetTokenValue<float>(GetTokenVersioned(vecToken)["Z"]);
//        }
//        else
//        {
//            result.x = GetTokenValue<float>(GetTokenVersioned(vecToken)[0]);
//            result.y = GetTokenValue<float>(GetTokenVersioned(vecToken)[1]);
//            result.z = GetTokenValue<float>(GetTokenVersioned(vecToken)[2]);
//        }
//
//        return result;
//    }
//
//    public static Quaternion GetQuaternion(JToken quatToken)
//    {
//        if (quatToken == null)
//            return Quaternion.identity;
//
//        Quaternion result;
//
//        if (GetTokenVersioned(quatToken).Type != JTokenType.Array)
//        {
//            result.w = GetTokenValue<float>(GetTokenVersioned(quatToken)["W"]);
//            result.x = GetTokenValue<float>(GetTokenVersioned(quatToken)["X"]);
//            result.y = GetTokenValue<float>(GetTokenVersioned(quatToken)["Y"]);
//            result.z = GetTokenValue<float>(GetTokenVersioned(quatToken)["Z"]);
//        }
//        else
//        {
//            result.x = GetTokenValue<float>(GetTokenVersioned(quatToken)[0]);
//            result.y = GetTokenValue<float>(GetTokenVersioned(quatToken)[1]);
//            result.z = GetTokenValue<float>(GetTokenVersioned(quatToken)[2]);
//            result.w = GetTokenValue<float>(GetTokenVersioned(quatToken)[3]);
//        }
//
//        return result;
//    }
//
//    public static void GetTransformation(JToken transToken, GameObject transToModify, bool useGlobalTrans)
//    {
//        if (useGlobalTrans)
//        {
//            transToModify.transform.rotation = GetQuaternion(GetTokenVersioned(transToken)["Orientation"]);
//            transToModify.transform.position = GetVector3(GetTokenVersioned(transToken)["Position"]);
//            transToModify.transform.localScale = GetVector3(GetTokenVersioned(transToken)["Scale"], Vector3.one);
//        }
//        else
//        {
//            tntChildLink childLink = transToModify.GetComponent<tntChildLink>();
//            if (childLink != null)
//            {
//                transToModify.transform.rotation = childLink.m_parent.transform.rotation * GetQuaternion(GetTokenVersioned(transToken)["Orientation"]);
//                transToModify.transform.position = childLink.m_parent.transform.rotation * GetVector3(GetTokenVersioned(transToken)["Position"]) + childLink.m_parent.transform.position;
//                transToModify.transform.localScale = Vector3.one;
//            }
//            else
//            {
//                transToModify.transform.localRotation = GetQuaternion(GetTokenVersioned(transToken)["Orientation"]);
//                transToModify.transform.localPosition = GetVector3(GetTokenVersioned(transToken)["Position"]);
//                transToModify.transform.localScale = GetVector3(GetTokenVersioned(transToken)["Scale"], Vector3.one);
//            }
//        }
//    }
//
//    public static T GetTokenValue<T>(JToken token, T defaultValue = default(T))
//    {
//        if (token != null)
//        {
//            if (token.Type != JTokenType.Object)
//                return token.Value<T>();
//
//            if (GetTokenVersioned(token) != null)
//            {
//                return GetTokenVersioned(token).Value<T>();
//            }
//            else
//            {
//                return defaultValue;
//            }
//            //return token.Value<T>();
//        }
//        else
//        {
//            return defaultValue;
//        }
//    }
//
//    public static JToken GetTokenVersioned(JToken token)
//    {
//        if (token.Type != JTokenType.Object)
//            return token;
//
//        if (token["property_value"] != null)
//        {
//            return token["property_value"];
//        }
//        else
//        {
//            return token;
//        }
//    }


}
